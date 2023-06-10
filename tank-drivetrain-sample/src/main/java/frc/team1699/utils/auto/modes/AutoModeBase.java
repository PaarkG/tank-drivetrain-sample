package frc.team1699.utils.auto.modes;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Robot;
import frc.team1699.utils.CrashTrackingRunnable;
import frc.team1699.utils.auto.actions.Action;

public abstract class AutoModeBase {
    public Pose2d startPose;
    private Thread thread = null;

    public void setStartPose() {
        Robot.drivetrain.resetOdometry(startPose);
    }

    public abstract void routine();
    public abstract void done();

    public void run() {
        thread = new Thread(new CrashTrackingRunnable() {
            public void runCrashTracked() {
                routine();
                done();
            }
        });
        thread.start();
    }

    public void runAction(Action action) {
        action.start();

        while(!action.isFinished()) {
            action.update();
        }

        action.done();
    }
}
