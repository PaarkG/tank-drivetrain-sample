package frc.team1699.utils.auto.modes;

import java.io.IOException;

import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import frc.team1699.subsystems.Manipulator.ManipulatorStates;
import frc.team1699.utils.auto.actions.FollowTrajectory;
import frc.team1699.utils.auto.actions.MoveArm;
import frc.team1699.utils.auto.actions.Peck;
import frc.team1699.utils.auto.actions.Place;
import frc.team1699.utils.auto.actions.RunIntake;
import frc.team1699.utils.auto.actions.StopIntake;
import frc.team1699.utils.auto.actions.UnPeck;

public class ScoreClearTwoCubes extends AutoModeBase {
    public ScoreClearTwoCubes() {
        try {
            startPose = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/ClearCubeToPiece.wpilib.json")).getInitialPose();
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
        runAction(new Peck());
        runAction(new RunIntake());
        runAction(new FollowTrajectory("ClearCubeToPiece", false));
        runAction(new UnPeck());
        runAction(new StopIntake());
        runAction(new FollowTrajectory("ReturnFromClearCube", false));
        runAction(new MoveArm(ManipulatorStates.CUBE_MID));
        runAction(new Place(false, 1));
        runAction(new MoveArm(ManipulatorStates.STORED));        
    }

    @Override
    public void done() {}
}
