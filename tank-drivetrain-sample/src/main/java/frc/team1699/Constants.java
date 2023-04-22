package frc.team1699;

public class Constants {
    public static class InputConstants {
        public static final int kDriveStickID = 0;
        public static final int kOperatorStickID = 1;
    }

    public static class DrivetrainConstants {
        public static final int kPortLeaderID = 21;
        public static final int kPortFollowerOneID = 22;
        public static final int kPortFollowerTwoID = 28;

        public static final int kStarLeaderID = 24;
        public static final int kStarFollowerOneID = 25;
        public static final int kStarFollowerTwoID = 29;

        public static final double kRotationDeadband = 0.05;
        public static final double kThrottleDeadband = 0.05;
        public static final boolean kUseDeadbands = true;
    }

    public static class GyroConstants {
        public static final double kPitchOffset = 1.0;
    }

    public static class PivotConstants {
        public static final int kPivotMotorID = 26;
        public static final int kPivotSwitchPort = 7;
    }

    public static class TelescopeConstants {
        public static final int kTelescopeMotorID = 27;
        public static final int kTelescopeSwitchPort = 4;
    }

    public static class IntakeConstants {
        public static final int kIntakeMotorID = 30;
    }

    public static class LEDConstants {
        public static final int kLEDPort = 9;
    }
}
