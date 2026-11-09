package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    private DcMotorEx outtakeMotor;
    private double encoderCPM = 28;

    public void initOuttake(HardwareMap hwMap) {
        outtakeMotor = hwMap.get(DcMotorEx.class, "OuttakeMotor");
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        outtakeMotor.setVelocityPIDFCoefficients(640, 0, 0, 16.1986);
    }

    public void setOuttakeVelocity(double velocity) {
        outtakeMotor.setVelocity(velocity);
    }

    public double getTicksPerSec() {
        return outtakeMotor.getVelocity();
    }

    public double getRPM() {
        return ((getTicksPerSec()/encoderCPM) * 60);
    }
}
