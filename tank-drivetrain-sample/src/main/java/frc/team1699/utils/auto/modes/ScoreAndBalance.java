package frc.team1699.utils.auto.modes;

import java.io.IOException;

import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import frc.team1699.subsystems.Manipulator.ManipulatorStates;
import frc.team1699.utils.auto.actions.FollowTrajectory;
import frc.team1699.utils.auto.actions.MoveArm;
import frc.team1699.utils.auto.actions.Place;

public class ScoreAndBalance extends AutoModeBase {
    public ScoreAndBalance() {
        try {
            startPose = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/CenterCubeToCharge.wpilib.json")).getInitialPose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStartPose();
    }

    @Override
    public void routine() {
        runAction(new MoveArm(ManipulatorStates.CUBE_HIGH));
        runAction(new Place(false, 1));
        runAction(new MoveArm(ManipulatorStates.STORED));
        runAction(new FollowTrajectory("CenterCubeToCharge", true));
    }

    @Override
    public void done() {
        System.out.println("Balancing...");
    }
}
