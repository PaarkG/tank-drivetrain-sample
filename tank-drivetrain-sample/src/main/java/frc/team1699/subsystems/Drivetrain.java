/* The drivetrain of a robot allows it to move. In the case of a team1699 tank drive, it has 3 motors on each side,
 * typically powering 4 wheels per side. With some 
 */

package frc.team1699.subsystems;

import java.io.IOException;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Robot;
import frc.team1699.Constants.DrivetrainConstants;
import frc.team1699.utils.Gyro;

public class Drivetrain {
    // Creating our gyroscope, which is used for odometry, primarily for autobalancing.
    private final Gyro gyro;

    // Establishing motors and encoders. Port is the left of the robot and starboard is the right, from the robot's POV.
    private final CANSparkMax portLeader;
    private final CANSparkMax portFollowerOne;
    private final CANSparkMax portFollowerTwo;
    private final RelativeEncoder portEncoder;

    private final CANSparkMax starLeader;
    private final CANSparkMax starFollowerOne;
    private final CANSparkMax starFollowerTwo;
    private final RelativeEncoder starEncoder;

    //balancing constants
    private final double kBalanceP = .023; // 0.022 WORKS WNE DAY 1 // 0.023 BETTER
    private final double kBalanceI = 0.0;
    private final double kBalanceD = 0.0015; // 0.0015 WORKS WNE DAY 1
    private final double kLevelPitch = 0;
    private final double kBalanceTolerance = 2.5;
    private final PIDController balanceController;

    // private final double pitchOffset = 1; USED IN FUTURE?

    private final DifferentialDriveOdometry odometry;
    private final DifferentialDriveKinematics kinematics;
    private final RamseteController ramsetinator;

    // private static final double firstGearRatio = 44.0/30.0;      A BUNCH OF MATH STUFF I DID
    // private static final double secondGearRatio = 50.0/44.0;
    // private static final double totalGearRatio = firstGearRatio * secondGearRatio;
    // private static final double wheelCircumferenceMeters = 0.478536;
    private static final double encoderMetersPerRotation = 0.2871;
    private static final double kTrackWidthMeters = 0.6731;

    private final TrajectoryConfig testTrajectoryConfig;
    public static Trajectory testTrajectory;

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(1, 3); //TODO: TUNE VALUES
    private final PIDController speedController = new PIDController(.05, 0,0);

    private static DriveStates wantedState;
    private static DriveStates currentState;

    public Drivetrain() {
        gyro = new Gyro();

        portLeader = new CANSparkMax(DrivetrainConstants.kPortLeaderID, MotorType.kBrushless);
        portFollowerOne = new CANSparkMax(DrivetrainConstants.kPortFollowerOneID, MotorType.kBrushless);
        portFollowerTwo = new CANSparkMax(DrivetrainConstants.kPortFollowerTwoID, MotorType.kBrushless);
        portEncoder = portLeader.getEncoder();
        portEncoder.setPositionConversionFactor(encoderMetersPerRotation);
        portEncoder.setVelocityConversionFactor(encoderMetersPerRotation);
        portFollowerOne.follow(portLeader);
        portFollowerTwo.follow(portLeader);

        starLeader = new CANSparkMax(DrivetrainConstants.kStarLeaderID, MotorType.kBrushless);
        starFollowerOne = new CANSparkMax(DrivetrainConstants.kStarFollowerOneID, MotorType.kBrushless);
        starFollowerTwo = new CANSparkMax(DrivetrainConstants.kStarFollowerTwoID, MotorType.kBrushless);
        starEncoder = starLeader.getEncoder();
        starEncoder.setPositionConversionFactor(encoderMetersPerRotation);
        portEncoder.setVelocityConversionFactor(encoderMetersPerRotation);
        starFollowerOne.follow(starLeader);
        starFollowerTwo.follow(starLeader);

        balanceController = new PIDController(kBalanceP, kBalanceI, kBalanceD);
        balanceController.setSetpoint(kLevelPitch);
        balanceController.setTolerance(kBalanceTolerance);

        odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), portEncoder.getPosition(), starEncoder.getPosition());
        kinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
        ramsetinator = new RamseteController();

        testTrajectoryConfig = new TrajectoryConfig(Units.inchesToMeters(120), Units.inchesToMeters(60)).setKinematics(kinematics);
        try {
            testTrajectory = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/StraightLine.wpilib.json"));
        } catch (IOException e) {
            e.printStackTrace();
            testTrajectory = null;
        }
    }

    public void update() {
        switch (currentState) {
            case MANUAL:
                runArcadeDrive(Robot.driveStick.getX(), -Robot.driveStick.getY());
                break;
            case BALANCING:
                runArcadeDrive(0, -balanceController.calculate(gyro.getPitch()));
                break;
            case SENSOR_TRACKING:
                break;
            case VISION_TRACKING:
                break;
            case TRAJECTORY_FOLLOWING:
                double timeStamp = Robot.autoTimer.get();
                if(timeStamp < testTrajectory.getTotalTimeSeconds()) {
                    Trajectory.State desiredPose = testTrajectory.sample(timeStamp);
                    ChassisSpeeds desiredChassisSpeeds = ramsetinator.calculate(getPose(), desiredPose);
                    driveWithPID(desiredChassisSpeeds.vxMetersPerSecond, desiredChassisSpeeds.omegaRadiansPerSecond);
                } else {
                    driveWithPID(0.0, 0.0);
                }
                break;
            default:
                break;
        }
        updateOdometry();
    }

    public void setWantedState(DriveStates wantedState) {
        Drivetrain.wantedState = wantedState;
        handleStateTransition();
    }

    public void handleStateTransition() {
        currentState = wantedState;
    }

    public DriveStates getCurrentState(){
        return Drivetrain.currentState;
    }

    public ChassisSpeeds ramseteCalc(Trajectory.State targetPose) {
        return ramsetinator.calculate(getPose(), targetPose);
    }

    public void runArcadeDrive(double rotate, double throttle) {
        double portOutput = 0.0;
        double starOutput = 0.0;

        if(DrivetrainConstants.kUseDeadbands && currentState == DriveStates.MANUAL && Math.abs(rotate) < DrivetrainConstants.kRotationDeadband) {
            rotate = 0;
        }

        if(DrivetrainConstants.kUseDeadbands && currentState == DriveStates.MANUAL && Math.abs(throttle) < DrivetrainConstants.kThrottleDeadband) {
            throttle = 0;
        }

        rotate = Math.copySign(rotate * rotate, rotate);
        throttle = Math.copySign(throttle * throttle, throttle);

        double maxInput = Math.copySign(Math.max(Math.abs(rotate), Math.abs(throttle)), rotate);

        if (rotate >= 0.0) {
            // First quadrant, else second quadrant
            if (throttle >= 0.0) {
                portOutput = maxInput;
                starOutput = rotate - throttle;
            } else {
                portOutput = rotate + throttle;
                starOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (throttle >= 0.0) {
                portOutput = rotate + throttle;
                starOutput = maxInput;
            } else {
                portOutput = maxInput;
                starOutput = rotate - throttle;
            }
        }
        portLeader.set(portOutput);
        starLeader.set(starOutput);
    }

    public void driveWithPID(double speed, double rot) {
        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(new ChassisSpeeds(speed, 0.0, rot));
        // double leftFF = feedforward.calculate(wheelSpeeds.leftMetersPerSecond);
        // double rightFF = feedforward.calculate(wheelSpeeds.rightMetersPerSecond);
        double leftOutput = speedController.calculate(portEncoder.getVelocity(), wheelSpeeds.leftMetersPerSecond);
        double rightOutput = -speedController.calculate(starEncoder.getVelocity(), wheelSpeeds.rightMetersPerSecond);
        portLeader.setVoltage(leftOutput);
        System.out.println(leftOutput);
        System.out.println("Left velocity " + portEncoder.getVelocity());
        System.out.println("Left target velocity " + wheelSpeeds.leftMetersPerSecond);
        starLeader.setVoltage(rightOutput);
        System.out.println(rightOutput);
        System.out.println("Right velocity " + starEncoder.getVelocity());
        System.out.println("Right target velocity " + wheelSpeeds.rightMetersPerSecond);
    }

    public void resetOdometryToTrajectory(Trajectory traj) {
        odometry.resetPosition(traj.getInitialPose().getRotation(), portEncoder.getPosition(), starEncoder.getPosition(), traj.getInitialPose());
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(gyro.getRotation2d(), portEncoder.getPosition(), starEncoder.getPosition(), pose);
    }

    public void updateOdometry() {
        odometry.update(gyro.getRotation2d(), portEncoder.getPosition(), starEncoder.getPosition());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public enum DriveStates {
        MANUAL,
        BALANCING,
        SENSOR_TRACKING,
        VISION_TRACKING,
        TRAJECTORY_FOLLOWING
    }
}
