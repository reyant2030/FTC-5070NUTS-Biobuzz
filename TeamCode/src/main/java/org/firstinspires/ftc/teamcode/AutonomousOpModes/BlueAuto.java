package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;

@Autonomous
public class BlueAuto extends LinearOpMode {
    Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() {
        waitForStart();
    }

}
