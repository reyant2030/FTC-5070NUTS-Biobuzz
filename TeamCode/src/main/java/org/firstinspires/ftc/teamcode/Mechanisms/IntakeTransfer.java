package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeTransfer {
    private DcMotor intakeTransfer;

    public void initTransfer(HardwareMap hwMap) {
        intakeTransfer = hwMap.get(DcMotor.class, "IntakeTransfer");
        intakeTransfer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeTransfer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setTransferPower(double power) {
        intakeTransfer.setPower(power);
    }
}
