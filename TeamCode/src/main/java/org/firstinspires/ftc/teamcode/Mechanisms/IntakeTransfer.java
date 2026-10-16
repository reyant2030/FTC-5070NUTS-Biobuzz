package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeTransfer {
    private DcMotor intakeTransferMotor;

    public void initIntakeTransfer(HardwareMap hwMap) {
        intakeTransferMotor = hwMap.get(DcMotor.class, "IntakeTransfer");
        intakeTransferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeTransferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setIntakeTransferPower(double power) {
        intakeTransferMotor.setPower(power);
    }
}
