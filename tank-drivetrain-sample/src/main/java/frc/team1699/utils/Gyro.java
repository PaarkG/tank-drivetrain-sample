package frc.team1699.utils;

import com.kauailabs.navx.frc.AHRS;
import frc.team1699.Constants.GyroConstants;

public class Gyro extends AHRS {
    public double getPitchWithOffset() {
        return getPitch() - GyroConstants.kPitchOffset;
    }
}
