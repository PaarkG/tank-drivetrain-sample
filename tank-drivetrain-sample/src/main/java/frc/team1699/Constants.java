package frc.team1699;

public class Constants {
    public static class InputConstants {
        public static final int kDriveStickID = 0;
        public static final int kOperatorStickID = 1;
    }

    public static class DrivetrainConstants {
        public static final int kPortLeaderID = 1;
        public static final int kPortFollowerOneID = 2;
        public static final int kPortFollowerTwoID = 3;

        public static final int kStarLeaderID = 4;
        public static final int kStarFollowerOneID = 5;
        public static final int kStarFollowerTwoID = 6;

        public static final double kRotationDeadband = 0.05;
        public static final double kThrottleDeadband = 0.05;
        public static final boolean kUseDeadbands = true;
    }

    public static class GyroConstants {
        public static final double kPitchOffset = 1.0;
    }
}
