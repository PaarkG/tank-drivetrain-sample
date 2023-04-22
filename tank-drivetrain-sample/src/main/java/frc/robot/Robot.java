// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.team1699.Constants.InputConstants;
import frc.team1699.subsystems.Drivetrain;
import frc.team1699.subsystems.Intake;
import frc.team1699.subsystems.Manipulator;
import frc.team1699.subsystems.Drivetrain.DriveStates;
import frc.team1699.subsystems.Intake.IntakeStates;
import frc.team1699.subsystems.Manipulator.ManipulatorStates;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */


public class Robot extends TimedRobot {
  public static final int kCoefficientOfPeck = 5;

  public static final Joystick driveStick = new Joystick(InputConstants.kDriveStickID);
  public static final Joystick opStick = new Joystick(InputConstants.kOperatorStickID);

  private final Drivetrain drivetrain;
  private final Manipulator manipulator;
  private final Intake intake;
  public static Timer autoTimer;

  Robot() {
    drivetrain = Drivetrain.getInstance();
    manipulator = Manipulator.getInstance();
    intake = Intake.getInstance();
  }

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    autoTimer = new Timer();
    autoTimer.start();

    drivetrain.setWantedState(DriveStates.TRAJECTORY_FOLLOWING);
  }

  @Override
  public void autonomousPeriodic() {
    drivetrain.update();
  }

  @Override
  public void teleopInit() {
    drivetrain.setWantedState(DriveStates.MANUAL);
  }

  @Override
  public void teleopPeriodic() {
    // DRIVER STICK
    if (driveStick.getRawButton(3)) {
      intake.setWantedState(IntakeStates.INTAKING);
    }

    if (driveStick.getRawButton(4)) {
      intake.setWantedState(IntakeStates.PLACING);
    }

    if (driveStick.getRawButtonReleased(3) || driveStick.getRawButtonReleased(4)) {
      intake.setWantedState(IntakeStates.IDLE);
    }

    // if(driveJoystick.getRawButtonPressed(5)) {
    // if(plow.getCurrentState() == PlowStates.OUT) {
    // plow.setWantedState(PlowStates.IN);
    // } else {
    // plow.setWantedState(PlowStates.OUT);
    // }
    // }

    if (driveStick.getRawButtonPressed(11)) {
      drivetrain.setWantedState(DriveStates.BALANCING);
    }

    if (driveStick.getRawButtonReleased(11)) {
      drivetrain.setWantedState(DriveStates.MANUAL);
    }

    if (driveStick.getPOV() == 0) {
      manipulator.incrementTelescopePosition();
    }

    if (driveStick.getPOV() == 180) {
      manipulator.decrementTelescopePosition();
    }

    if (driveStick.getPOV() == 90) {
      manipulator.incrementPivotPosition();
    }

    if (driveStick.getPOV() == 270) {
      manipulator.decrementPivotPosition();
    }

    // OPERATOR STICK
    // FLOOR POSITION
    if (opStick.getRawButtonPressed(6)) {
      manipulator.setWantedState(ManipulatorStates.FLOOR);
    }

    if (opStick.getPOV() == 0) {
      manipulator.incrementTelescopePosition();
    }

    if (opStick.getPOV() == 180) {
      manipulator.decrementTelescopePosition();
    }

    if (opStick.getPOV() == 90) {
      manipulator.incrementPivotPosition();
    }

    if (opStick.getPOV() == 270) {
      manipulator.decrementPivotPosition();
    }

    // LOW
    if (opStick.getRawButtonPressed(11)) {
      manipulator.setWantedState(ManipulatorStates.CUBE_MID);
    }

    // // MID
    // if (opJoystick.getRawButtonPressed(9)) {
    // manipulator.setWantedState(ManipulatorStates.CUBE_MID);
    // }
    // HIGH SHOOT CUBE
    if (opStick.getRawButton(9)) {
      manipulator.setWantedState(ManipulatorStates.CUBE_HIGH);
    }

    // HIGH
    if (opStick.getRawButtonPressed(7)) {
      manipulator.setWantedState(ManipulatorStates.HIGH);
    }

    // SHELF
    if (opStick.getRawButtonPressed(12)) {
      manipulator.setWantedState(ManipulatorStates.SHELF);
    }

    // STORED BACK
    if (opStick.getRawButtonPressed(10)) {
      manipulator.setWantedState(ManipulatorStates.STORED);
    }

    // CONE MID
    if (opStick.getRawButtonPressed(8)) {
      manipulator.setWantedState(ManipulatorStates.MID);
    }

    // if(opJoystick.getRawButton(6)) {
    // manipulator.incrementPivotPosition();
    // }

    // if(opJoystick.getRawButton(5)) {
    // manipulator.decrementPivotPosition();
    // }

    // PECK PECK
    if (opStick.getRawButtonPressed(5)) {
      for (int i = 0; i < kCoefficientOfPeck; i++) {
        manipulator.incrementPivotPosition();
      }
    }

    if (opStick.getRawButton(5) && (manipulator.getCurrentState() == ManipulatorStates.SHELF || manipulator.getCurrentState() == ManipulatorStates.STORED)) {
      intake.setWantedState(IntakeStates.INTAKING);
    }

    if (opStick.getRawButtonReleased(5)) {
      for (int i = 0; i < kCoefficientOfPeck; i++) {
        manipulator.decrementPivotPosition();
      }
      intake.setWantedState(IntakeStates.IDLE);
    }

    if (opStick.getRawButton(2)) {
      manipulator.resetTelescopeEncoder();
      manipulator.resetPivotEncoder();
    }
    drivetrain.update();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
