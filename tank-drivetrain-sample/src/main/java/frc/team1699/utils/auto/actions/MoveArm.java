package frc.team1699.utils.auto.actions;

import frc.robot.Robot;
import frc.team1699.subsystems.Manipulator;
import frc.team1699.subsystems.Manipulator.ManipulatorStates;

public class MoveArm implements Action {
    private ManipulatorStates position;
    private Manipulator manipulator;

    public MoveArm(ManipulatorStates wantedPosition) {
        this.position = wantedPosition;
        this.manipulator = Robot.manipulator;
    }

    @Override
    public void start() {
        manipulator.setWantedState(position);
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        if(manipulator.isDoneMoving()) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {}
}
