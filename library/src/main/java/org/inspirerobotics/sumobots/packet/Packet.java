package org.inspirerobotics.sumobots.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.inspirerobotics.sumobots.FmsComponent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;

public class Packet {

    private static final Gson gson = new Gson();
    private final PacketPath path;
    private final String action;
    private final Optional<JsonObject> data;

    Packet(String action, PacketPath path, Optional<JsonObject> data) {
        if (action == null)
            throw new IllegalArgumentException("Action cannot be null!");

        if (path == null)
            throw new IllegalArgumentException("Path cannot be null!");

        if (data == null)
            data = Optional.empty();


        this.data = data;
        this.action = action;
        this.path = path;
    }

    public static Packet create(String action, FmsComponent src, FmsComponent dest) {
        return new Packet(action, new PacketPath(src, dest), Optional.empty());
    }

    public static Packet create(String action, FmsComponent src, FmsComponent dest, Optional<Object> value) {
        Optional<JsonObject> element = value.map(Packet::dataToJsonObject);
        return new Packet(action, new PacketPath(src, dest), element);
    }

    public static Packet create(String action, PacketPath path) {
        return new Packet(action, path, Optional.empty());
    }

    public static Packet create(String action, PacketPath path, Optional<Object> value) {
        Optional<JsonObject> element = value.map(Packet::dataToJsonObject);
        return new Packet(action, path, element);
    }

    private static JsonObject dataToJsonObject(Object o) {
        return gson.toJsonTree(o).getAsJsonObject();
    }

    public static Packet fromJSON(String input) {
        Type mapType = new TypeToken<HashMap<String, JsonElement>>() {
        }.getType();
        HashMap<String, JsonElement> map = gson.fromJson(input, mapType);

        String action =  map.get("action").getAsString();
        Optional<JsonObject> data = Optional.ofNullable(map.get("data").getAsJsonObject());

        return new Packet(action, new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION), data);
    }

    public String toJSON() {
        HashMap<String, Object> values = new HashMap<>();

        values.put("action", action);
        values.put("path", path);

        data.ifPresent(x -> values.put("data", x));

        return gson.toJson(values);
    }

    public String getAction() {
        return action;
    }

    public <T> Optional getDataAs(Class<T> t) {
        return data.map(data -> gson.fromJson(data, t));
    }

    @Override
    public String toString() {
        return String.format("Packet[src = %s; dest = %s; action=%s; data=%s]", path.getSource(),
                path.getDestination(), action, data);
    }
}
