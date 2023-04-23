package frc.team1699.utils.auto.actions;

import frc.team1699.subsystems.Manipulator;
import frc.team1699.subsystems.Manipulator.ManipulatorStates;

public class MoveArm implements Action {
    private ManipulatorStates position;
    public MoveArm(ManipulatorStates wantedPosition) {
        this.position = wantedPosition;
    }

    @Override
    public void start() {
        Manipulator.getInstance().setWantedState(position);
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        if(Manipulator.getInstance().isDoneMoving()) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {}
}
