package frc.team1699.utils.auto.modes;

import frc.team1699.subsystems.Manipulator.ManipulatorStates;
import frc.team1699.utils.auto.actions.FollowTrajectory;
import frc.team1699.utils.auto.actions.MoveArm;
import frc.team1699.utils.auto.actions.Place;

public class ScoreMobilityBalance extends AutoModeBase {
    @Override
    public void routine() {
        runAction(new MoveArm(ManipulatorStates.CUBE_HIGH));
        runAction(new Place(false, 1));
        runAction(new MoveArm(ManipulatorStates.STORED));
        runAction(new FollowTrajectory("CenterCubeMobilityCharge", true));
    }

    @Override
    public void done() {
        System.out.println("Balancing...");
    }
}
