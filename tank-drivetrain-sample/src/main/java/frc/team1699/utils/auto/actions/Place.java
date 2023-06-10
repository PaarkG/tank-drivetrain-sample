package frc.team1699.utils.auto.actions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.team1699.subsystems.Intake.IntakeStates;

public class Place implements Action {
    private final Timer timer = new Timer();
    private boolean peck;
    private double placeTime;

    public Place(boolean peck, double placeTime) {
        this.peck = peck;
        this.placeTime = placeTime;
    }

    @Override
    public void start() {
        if(peck) {
            for(int i = 0; i < 5; i++) {
                Robot.manipulator.incrementPivotPosition();
            }
        }
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        Robot.intake.setWantedState(IntakeStates.PLACING_AUTO);
    }

    @Override
    public boolean isFinished() {
        if(timer.get() > placeTime) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        if(peck) {
            for(int i = 0; i < 5; i++) {
                Robot.manipulator.decrementPivotPosition();
            }
        }
        Robot.intake.setWantedState(IntakeStates.IDLE);
    }
    
}
