package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;

@Autonomous
public class BlueAuto extends LinearOpMode {
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    AdjustableHood adjustableHood = new AdjustableHood();
    TransferStopper transferStopper = new TransferStopper();
    Outtake outtake = new Outtake();
    Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() {

        intakeTransfer.initIntakeTransfer(hardwareMap);
        transferStopper.initTransferStopper(hardwareMap);
        outtake.initOuttake(hardwareMap);
        adjustableHood.initHood(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            transferStopper.setTransferPosition(0.075);
            sleep(500);
            outtake.setOuttakePower(1.0);
            sleep(1500);
            transferStopper.setTransferPosition(0);
            sleep(500);
            intakeTransfer.setIntakeTransferPower(0.75);
            sleep(5000);
            outtake.setOuttakePower(0.0);
            intakeTransfer.setIntakeTransferPower(0.0);
            transferStopper.setTransferPosition(0.075);
            drivetrain.drive(-0.5, 0.0,0.0);
            sleep(2000);
            drivetrain.drive(0.0,0.0,0.5);
            sleep(2000);
            drivetrain.drive(-0.5, 0.0,0.0);
            sleep(2000);
            drivetrain.drive(0.0, 0.0,0.0);
        }
    }

}
