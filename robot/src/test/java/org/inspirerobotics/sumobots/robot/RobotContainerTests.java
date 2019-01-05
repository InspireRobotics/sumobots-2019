package org.inspirerobotics.sumobots.robot;

import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.driverstation.DriverstationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RobotContainerTests {

    private RobotContainer container;
    private TestRobot robot;

    @BeforeEach
    void setUp() {
        robot = new TestRobot();
        container = new RobotContainer(robot);
    }

    @Test
    void shutdownBasicTest(){
        container.onShutdown();

        Assertions.assertTrue(robot.shutdown);
    }

    @Test
    void initBasicTest(){
        container.init();

        Assertions.assertTrue(robot.init);
    }

    @Test
    public void updateRobotEnabledTest(){
        DriverstationTests.setSingletonState(ComponentState.ENABLED);

        for(int i = 0; i < 5; i++){
            container.updateRobot();
        }

        Assertions.assertEquals(5, robot.enableCount);
    }

    @Test
    public void updateRobotDisabledTest(){
        DriverstationTests.setSingletonState(ComponentState.DISABLED);

        for(int i = 0; i < 5; i++){
            container.updateRobot();
        }

        Assertions.assertEquals(5, robot.disableCount);
    }
}
class TestRobot implements RobotBase{

    boolean shutdown;
    boolean init;

    int enableCount = 0;
    int disableCount = 0;

    @Override
    public void init() {
        init = true;
    }

    @Override
    public void enablePeriodic() {
        enableCount++;
    }

    @Override
    public void disablePeriodic() {
        disableCount++;
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }
}