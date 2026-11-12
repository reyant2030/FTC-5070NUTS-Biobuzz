package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Depollenator;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class RedAutoLeavePark extends LinearOpMode {

    Depollenator depollenator = new Depollenator();
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    TransferStopper transferStopper = new TransferStopper();
    AdjustableHood adjustableHood = new AdjustableHood();
    Outtake outtake = new Outtake();

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(54.9421, 8, 90);
    private final Pose path1 = poseFactory.of(123.5321, 31.7682, 270);
    private final Pose path1Control1 = poseFactory.of(104.2119, 38.5932, 0);

    //public Command adjustableHood = Command.build()...
    //TS means Transfer Stopper
    public Command intakeOn = Command.build()
            .setStart(() -> intakeTransfer.setTransferVelocity(1394))
            .requiring(intakeTransfer);

    public Command intakeOff = Command.build()
            .setStart(() -> intakeTransfer.setTransferVelocity(0.0))
            .requiring(intakeTransfer);

    //public Command adjustableHood = Command.build()...
    //TS means Transfer Stopper
    public Command TS = Command.build()
            .setStart(() -> transferStopper.setPosition(0.0))
            .requiring(transferStopper);

    public Command StopTS = Command.build()
            .setStart(() -> transferStopper.setPosition(0.25))
            .requiring(transferStopper);

    public Command shooterOn = Command.build()
            .setStart(() -> outtake.setOuttakeVelocity(1500))
            .setDone(() -> outtake.getTicksPerSec() > 1490)
            .requiring(outtake);
    public Command shooterOff = Command.build()
            .setStart(() -> outtake.setOuttakeVelocity(0))
            .requiring(outtake);

    public Command depollenatorUp = Command.build()
            .setStart(() -> depollenator.setPosition(0.995))
            .requiring(depollenator);
    public Command depollenatorDown = Command.build()
            .setStart(() -> depollenator.setPosition(0.4))
            .requiring(depollenator);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                TS,
                shooterOn,
                StopTS,
                intakeOn,
                waitMs(3000),
                intakeOff,
                shooterOff,
                TS,
                follow(follower, path1())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        //UNDO THIS WHEN YOU HAVE CONSTANTS
        //follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.curve(start, path1Control1, path1).constant(path1);
    }
}
