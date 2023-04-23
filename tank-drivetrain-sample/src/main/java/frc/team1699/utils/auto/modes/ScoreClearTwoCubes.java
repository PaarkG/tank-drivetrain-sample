package frc.team1699.utils.auto.modes;

import frc.team1699.subsystems.Manipulator.ManipulatorStates;
import frc.team1699.utils.auto.actions.FollowTrajectory;
import frc.team1699.utils.auto.actions.MoveArm;
import frc.team1699.utils.auto.actions.Peck;
import frc.team1699.utils.auto.actions.Place;
import frc.team1699.utils.auto.actions.RunIntake;
import frc.team1699.utils.auto.actions.StopIntake;
import frc.team1699.utils.auto.actions.UnPeck;

public class ScoreClearTwoCubes extends AutoModeBase {
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
    public void done() {
        // TODO Auto-generated method stub
        
    }
}
