package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Ramp {
    Servo rampServo1;
    Servo rampServo2;
    public void initRamp(HardwareMap hwMap) {
        rampServo1 = hwMap.get(Servo.class, "rampServo1");
        rampServo2 = hwMap.get(Servo.class, "rampServo2");
    }

    public void moveRamp() {
        if ((rampServo1.getPosition() == 0.0) && (rampServo2.getPosition() == 0.0)) {
            rampServo1.setPosition(0.5);
            rampServo2.setPosition(0.5);
        }
        else {
            rampServo1.setPosition(0.0);
            rampServo2.setPosition(0.0);
        }
    }
}
