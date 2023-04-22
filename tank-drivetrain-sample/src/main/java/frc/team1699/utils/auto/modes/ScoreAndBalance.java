package frc.team1699.utils.auto.modes;

import frc.team1699.utils.auto.actions.FollowTrajectory;

public class ScoreAndBalance extends AutoModeBase {
    @Override
    public void routine() {
        runAction(new FollowTrajectory("CenterCubeToCharge", true));
    }

    @Override
    public void done() {
        System.out.println("Balancing...");
    }
}
