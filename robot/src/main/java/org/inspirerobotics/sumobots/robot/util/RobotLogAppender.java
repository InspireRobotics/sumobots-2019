package org.inspirerobotics.sumobots.robot.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.inspirerobotics.sumobots.HighPriorityLogFilter;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.robot.driverstation.Driverstation;

public class RobotLogAppender extends AbstractAppender{

    public RobotLogAppender() {
        super(LogManager.ROOT_LOGGER_NAME, new HighPriorityLogFilter(), null);
    }

    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        Level level = event.getLevel();

        if(level.equals(Level.INFO)){
            Driverstation.reportInfo(message);
        }else if(level.equals(Level.WARN)){
            Driverstation.reportWarning(message);
        }else if(level.equals(Level.ERROR) || level.equals(Level.FATAL)){
            Driverstation.reportError(message);
        }
    }

    @VisibleForTesting
    public static void init(Appender appender){
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        appender.start();
        config.addAppender(appender);
        ctx.getRootLogger().addAppender(appender);
    }

    public static void init(){
        init(new RobotLogAppender());
    }
}
