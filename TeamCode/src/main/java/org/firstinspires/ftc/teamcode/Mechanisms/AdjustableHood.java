package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

public class AdjustableHood {
    CRServo hoodServo;

    public void initHoodServo(HardwareMap hwMap) {
        hoodServo = hwMap.get(CRServo.class, "HoodServo");
    }

    public void setPower(double power) {
        hoodServo.setPower(power);
    }
}
