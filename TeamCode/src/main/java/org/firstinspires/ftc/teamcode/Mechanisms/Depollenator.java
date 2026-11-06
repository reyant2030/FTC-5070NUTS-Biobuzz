package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Depollenator {
    Servo depollenator;

    public void initDepollenator(HardwareMap hwMap) {
        depollenator = hwMap.get(Servo.class, "Depollenator");
    }

    public void setPosition(double position) {
        depollenator.setPosition(position);
    }
}
