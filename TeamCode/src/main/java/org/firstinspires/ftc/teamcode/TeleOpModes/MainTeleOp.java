package org.firstinspires.ftc.teamcode.TeleOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends OpMode {
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    AdjustableHood adjustableHood = new AdjustableHood();
    TransferStopper transferStopper = new TransferStopper();
    Outtake outtake = new Outtake();
    Drivetrain drivetrain = new Drivetrain();
    double forward, strafe, rotate;

    @Override
    public void init() {
        drivetrain.initDrivetrain(hardwareMap);
        intakeTransfer.initIntakeTransfer(hardwareMap);
        transferStopper.initTransferStopper(hardwareMap);
        outtake.initOuttake(hardwareMap);
        adjustableHood.initHood(hardwareMap);
    }

    @Override
    public void loop() {
        forward = -gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = gamepad1.left_stick_x;
        drivetrain.drive(forward, strafe, rotate);

        if (gamepad1.right_bumper) {
            intakeTransfer.setIntakeTransferPower(1);
        }
        else {
            intakeTransfer.setIntakeTransferPower(0);
        }

        if (gamepad2.right_bumper) {
            intakeTransfer.setIntakeTransferPower(1);
        }
        else {
            intakeTransfer.setIntakeTransferPower(0);
        }

        if (gamepad2.left_bumper) {
            outtake.setOuttakePower(0.45);
        }
        else {
            outtake.setOuttakePower(0);
        }

        if (gamepad2.x) {
            transferStopper.setTransferPosition(0);
        }
        else {
            transferStopper.setTransferPosition(0.36);
        }
    }
}
