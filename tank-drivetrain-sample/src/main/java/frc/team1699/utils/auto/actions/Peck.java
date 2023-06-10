package frc.team1699.utils.auto.actions;

import frc.robot.Robot;

public class Peck implements Action {
    @Override
    public void start() {
        for(int i = 0; i < 5; i++) {
            Robot.manipulator.incrementPivotPosition();
        }
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void done() {}
}
