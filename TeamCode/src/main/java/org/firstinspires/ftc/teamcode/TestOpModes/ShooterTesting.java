package org.firstinspires.ftc.teamcode.TestOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;

@TeleOp(name = "ShooterTest")
public class ShooterTesting extends OpMode {
    Outtake outtake = new Outtake();

    @Override
    public void init() {
        outtake.initOuttake(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            outtake.setOuttakePower(0.5);
        }

        if (gamepad1.x) {
            outtake.setOuttakePower(0);
        }
    }
}
