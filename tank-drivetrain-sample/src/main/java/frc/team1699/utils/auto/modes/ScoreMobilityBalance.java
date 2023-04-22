package frc.team1699.utils.auto.modes;

import frc.team1699.utils.auto.actions.FollowTrajectory;

public class ScoreMobilityBalance extends AutoModeBase {
    @Override
    public void routine() {
        runAction(new FollowTrajectory("CenterCubeMobilityCharge", true));
    }

    @Override
    public void done() {
        System.out.println("Balancing...");
    }
}
