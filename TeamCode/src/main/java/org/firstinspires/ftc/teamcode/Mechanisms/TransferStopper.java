package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TransferStopper {
    Servo stopperServo;

    public void initTransferStopper(HardwareMap hwMap) {
        stopperServo = hwMap.get(Servo.class, "TransferStopper");
    }

    public void setPosition(double position) {
        stopperServo.setPosition(position);
    }
}
