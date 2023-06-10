package frc.team1699.utils.auto.actions;

import frc.robot.Robot;
import frc.team1699.subsystems.Intake.IntakeStates;

public class RunIntake implements Action {
    @Override

    public void start(){
        Robot.intake.setWantedState(IntakeStates.INTAKING_AUTO);  
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
