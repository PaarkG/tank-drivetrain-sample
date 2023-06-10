package frc.team1699.utils;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.team1699.Constants.GyroConstants;

public class Gyro extends AHRS {
    public double getPitchWithOffset() {
        return getPitch() - GyroConstants.kPitchOffset;
    }

    public Rotation2d getRotation2d(){
        return new Rotation2d(Math.toRadians(this.getYaw()));
    }
}
