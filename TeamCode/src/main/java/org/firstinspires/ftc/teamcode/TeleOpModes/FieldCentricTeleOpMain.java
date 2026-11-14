package org.firstinspires.ftc.teamcode.TeleOpModes;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Depollenator;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;

@TeleOp(name = "FieldCentricTeleOpMain")
public class FieldCentricTeleOpMain extends OpMode {

    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    Drivetrain drivetrain = new Drivetrain();
    double forward, strafe, rotate;
    Depollenator depollenator = new Depollenator();
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    TransferStopper transferStopper = new TransferStopper();
    AdjustableHood adjustableHood = new AdjustableHood();
    Outtake outtake = new Outtake();
//
    private IMU imu;



    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );
        imu.initialize(parameters);
        drivetrain.initDrivetrain(hardwareMap);
        depollenator.initDepollenator(hardwareMap);
        intakeTransfer.initTransfer(hardwareMap);
        transferStopper.initTransferStopper(hardwareMap);
        adjustableHood.initHoodServo(hardwareMap);
        outtake.initOuttake(hardwareMap);

        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");


        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.right_bumper) {
            intakeTransfer.setTransferPower(1);
        }
        else if (gamepad2.a) {
            intakeTransfer.setTransferPower(0.3);
        }
        else if (gamepad1.right_trigger > 0) {
            intakeTransfer.setTransferPower(-1);
        }
        else {
            intakeTransfer.setTransferPower(0);
        }

        if (gamepad1.left_bumper) {
            depollenator.setPosition(0.995);
        }
        else {
            depollenator.setPosition(0.4);
        }

        if (gamepad2.x) {
            transferStopper.setPosition(0);
        }
        else {
            transferStopper.setPosition(0.25);
        }

        if (gamepad2.left_bumper) {
            outtake.setOuttakeVelocity(1500);
        }
        else if (gamepad2.right_bumper){
            outtake.setOuttakeVelocity(1400);
        }
        else if (gamepad2.right_trigger > 0) {
            outtake.setOuttakeVelocity(1600);
        }
        else if (gamepad2.left_trigger > 0) {
            outtake.setOuttakeVelocity(1800);
        }
        else {
            outtake.setOuttakeVelocity(0);
        }

        if (gamepad2.dpad_right) {
            adjustableHood.setPower(1);
        }
        else if (gamepad2.dpad_up) {
            adjustableHood.setPower(-1);
        }
        else {
            adjustableHood.setPower(0);
        }

        telemetry.addData("RPM", outtake.getRPM());
    }
}
