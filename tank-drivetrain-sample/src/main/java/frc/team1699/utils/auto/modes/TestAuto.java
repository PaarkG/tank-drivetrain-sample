package frc.team1699.utils.auto.modes;

import java.io.IOException;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import frc.team1699.utils.auto.actions.FollowTrajectory;

public class TestAuto extends AutoModeBase {
    private Trajectory traj;
    private boolean trajLoaded = true;
    public TestAuto() {
        try {
            traj = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/StraightLine.wpilib.json"));
            startPose = traj.getInitialPose();
            setStartPose();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load trajectory");
            trajLoaded = false;
        }
    }

    @Override
    public void routine() {
        if(trajLoaded) {
            runAction(new FollowTrajectory(traj, false));
        }
    }

    @Override
    public void done() {
        System.out.println("Done");
    }
}