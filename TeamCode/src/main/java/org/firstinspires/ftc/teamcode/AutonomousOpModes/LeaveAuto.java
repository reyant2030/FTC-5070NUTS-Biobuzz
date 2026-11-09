package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto")
public class LeaveAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;

        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");

        //From other auto; reverse motors as needed; might wanna uncomment this idk :)
        //frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //frontRightMotor.setDirection(DcMotor.Direction.REVERSE);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        //This goes back for a very short amount of time, so position the bot at the top-left or
        //bottom-right of the field depending on blue/red and face it near the corner, but right
        //next to the red/blue park areas, but intake facing towards the top for red and bottom for
        //blue.

        frontRightMotor.setPower(1);
        backRightMotor.setPower(-1);
        frontLeftMotor.setPower(-1);
        backLeftMotor.setPower(1);
        sleep(500);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);

        stop();
    }
}
