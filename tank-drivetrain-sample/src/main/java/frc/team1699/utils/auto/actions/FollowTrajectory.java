package frc.team1699.utils.auto.actions;

import java.io.IOException;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.team1699.subsystems.Drivetrain;
import frc.team1699.subsystems.Drivetrain.DriveStates;

public class FollowTrajectory implements Action {
    private final Drivetrain drivetrain = Robot.drivetrain;
    private final Timer timer = new Timer();
    private final boolean balanceAfter;
    private Trajectory traj;
    
    public FollowTrajectory(Trajectory traj, boolean balanceAfter) {
        this.traj = traj;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        Trajectory.State targetPose = traj.sample(timer.get());
        ChassisSpeeds desiredSpeeds = drivetrain.ramseteCalc(targetPose);
        drivetrain.driveWithPID(desiredSpeeds.vxMetersPerSecond, desiredSpeeds.omegaRadiansPerSecond);
    }

    @Override
    public boolean isFinished() {
        if(timer.get() > traj.getTotalTimeSeconds()) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        if(balanceAfter) {
            drivetrain.setWantedState(DriveStates.BALANCING);
        }
        drivetrain.setWantedState(DriveStates.MANUAL);
    }
}
