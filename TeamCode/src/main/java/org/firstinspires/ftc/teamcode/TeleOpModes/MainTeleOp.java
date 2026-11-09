package org.firstinspires.ftc.teamcode.TeleOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Depollenator;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends OpMode {
    Drivetrain drivetrain = new Drivetrain();
    double forward, strafe, rotate;
    Depollenator depollenator = new Depollenator();
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    TransferStopper transferStopper = new TransferStopper();
    AdjustableHood adjustableHood = new AdjustableHood();
    Outtake outtake = new Outtake();


    @Override
    public void init() {
        drivetrain.initDrivetrain(hardwareMap);
        depollenator.initDepollenator(hardwareMap);
        intakeTransfer.initTransfer(hardwareMap);
        transferStopper.initTransferStopper(hardwareMap);
        adjustableHood.initHoodServo(hardwareMap);
        outtake.initOuttake(hardwareMap);
    }

    @Override
    public void loop() {
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drivetrain.fieldCentric(forward, strafe, rotate);

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
