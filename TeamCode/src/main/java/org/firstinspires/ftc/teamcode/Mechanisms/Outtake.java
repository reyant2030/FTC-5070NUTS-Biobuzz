package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    DcMotor outtakeMotor;

    public void initOuttake(HardwareMap hwMap) {
        outtakeMotor = hwMap.get(DcMotor.class, "OuttakeMotor");
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setOuttakePower(double power) {
        outtakeMotor.setPower(power);
    }
}
