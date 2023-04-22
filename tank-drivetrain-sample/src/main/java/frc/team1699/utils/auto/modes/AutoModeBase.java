package frc.team1699.utils.auto.modes;

import edu.wpi.first.math.geometry.Pose2d;
import frc.team1699.subsystems.Drivetrain;
import frc.team1699.utils.auto.actions.Action;

public abstract class AutoModeBase {
    public Pose2d startPose;

    public void setStartPose() {
        Drivetrain.getInstance().resetOdometry(startPose);
    }

    public abstract void routine();
    public abstract void done();

    public void run() {
        routine();
        done();
    }

    public void runAction(Action action) {
        action.start();

        while(!action.isFinished()) {
            action.update();
        }

        action.done();
    }
}
