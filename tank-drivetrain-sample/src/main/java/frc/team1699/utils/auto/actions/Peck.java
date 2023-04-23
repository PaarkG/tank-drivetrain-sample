package frc.team1699.utils.auto.actions;

import frc.team1699.subsystems.Manipulator;

public class Peck implements Action {
    @Override
    public void start() {
        for(int i = 0; i < 5; i++) {
            Manipulator.getInstance().incrementPivotPosition();
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
