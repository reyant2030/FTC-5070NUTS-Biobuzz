package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AdjustableHood {
    Servo stopperServo;

    public void initHood(HardwareMap hwMap) {
        stopperServo = hwMap.get(Servo.class, "Stopper");
    }

    public void setHoodPosition(double position) {
        stopperServo.setPosition(position);
    }
}
