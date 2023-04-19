// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.team1699.Constants.InputConstants;
import frc.team1699.subsystems.Drivetrain;
import frc.team1699.subsystems.Drivetrain.DriveStates;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */


public class Robot extends TimedRobot {
  public static final Joystick driveStick = new Joystick(InputConstants.kDriveStickID);
  public static final Joystick opStick = new Joystick(InputConstants.kOperatorStickID);

  private final Drivetrain drivetrain;
  public static Timer autoTimer;

  Robot() {
    drivetrain = Drivetrain.getInstance();
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
