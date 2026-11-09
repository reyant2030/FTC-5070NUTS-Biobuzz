package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AdjustableHood;
import org.firstinspires.ftc.teamcode.Mechanisms.Depollenator;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeTransfer;
import org.firstinspires.ftc.teamcode.Mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.Mechanisms.TransferStopper;

//import com.qualcomm.robotcore.hardware.Dc

//import org.firstinspires.ftc.teamcode.pedro.Constants;

//UNCOMMENT PEDRO CONSTANTS AND THE OTHER PEDRO THING ONCE IT IS OK (PEDRO INSTALLED)


@Autonomous(name = "FlowerIntakeTwoTipAuto", group = "Autonomous")
public class FlowerIntakeTwoTipAutoFull extends LinearOpMode {

    Depollenator depollenator = new Depollenator();
    IntakeTransfer intakeTransfer = new IntakeTransfer();
    TransferStopper transferStopper = new TransferStopper();
    AdjustableHood adjustableHood = new AdjustableHood();
    Outtake outtake = new Outtake();

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(57, 9, 270);
    private final Pose shootAndToFlower1 = poseFactory.of(28.3104, 46.6007, 180);
    private final Pose intakeFlower1 = poseFactory.of(15.9789, 46.4795, 180);
    private final Pose backUpFromFlower1 = poseFactory.of(22.7824, 46.452, 180);
    private final Pose toShootTip2Start = poseFactory.of(22.7824, 46.452, 180);
    private final Pose toShootTip2 = poseFactory.of(61.4848, 132.3348, 90);
    private final Pose toShootTip2Control1 = poseFactory.of(16.6733, 116.6416, 0);
    private final Pose toShootTip2Control2 = poseFactory.of(62.6684, 108.5625, 0);
    private final Pose toIntakeFlower2 = poseFactory.of(47.0654, 116.6664, 90);
    private final Pose toIntakeFlower2Control1 = poseFactory.of(61.1077, 116.0322, 0);
    private final Pose intakeFlower2 = poseFactory.of(46.9545, 122.8336, 90);
    private final Pose backUpFlower2 = poseFactory.of(47.0872, 111.6558, 90);
    private final Pose toShoot = poseFactory.of(61.8346, 131.8602, 90);
    private final Pose toShootControl1 = poseFactory.of(58.3183, 114.5351, 0);
    private final Pose toEnd = poseFactory.of(17.5757, 115.4441, 90);
    private final Pose toEndControl1 = poseFactory.of(52.3279, 103.5992, 0);


    public Command intakeOn = Command.build()
            .setStart(() -> intakeTransfer.setTransferPower(0.4))
            .requiring(intakeTransfer);

    public Command intakeOff = Command.build()
            .setStart(() -> intakeTransfer.setTransferPower(0.0))
            .requiring(intakeTransfer);

    //public Command adjustableHood = Command.build()...
    public Command TS = Command.build()
            .setStart(() -> transferStopper.setPosition(0.995))
            .requiring(transferStopper);

    public Command StopTS = Command.build()
            .setStart(() -> transferStopper.setPosition(0.4))
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

                //Shoot Your Preload: Run shooter then wait to get to speed then run transfer
                TS,
                shooterOn,
                StopTS,
                intakeOn,
                waitMs(3000),
                intakeOff,
                shooterOff,
                TS,

                parallel (
                        follow(follower, shootAndToFlower1()),
                        depollenatorDown,
                        intakeOn
                ),

                //While the path is running, put the flower ramp down
                //Also run the intake/transfer
                //parallel()
                follow(follower, intakeFlower1()),

                parallel (
                        follow(follower, backUpFromFlower1()),
                        intakeOff
                ),

                //During the path when you back up, turn off intake / transfer
                //Don't put flower ramp down yet
                parallel(
                        follow(follower, toShootTip2()),
                        depollenatorDown,
                        intakeOff
                ),

                TS,
                shooterOn,
                StopTS,
                intakeOn,
                waitMs(3000),
                intakeOff,
                shooterOff,
                TS,

                //While path is running, run intake
                //While path is running, put flower down
                parallel(
                        follow(follower, toIntakeFlower2()),
                        depollenatorDown,
                        intakeOn
                ),
                TS,
                follow(follower, intakeFlower2()),
                //While running, turn off intake/transfer
                //Put down flower ramp
                //Turn on outtake
                follow(follower, backUpFlower2()),
                intakeOff,
                depollenatorUp,



                //After path, Turn on transfer for a se time
                follow(follower, toShoot()),
                shooterOn,
                StopTS,
                intakeOn,
                waitMs(3000),
                intakeOff,
                shooterOff,
                TS,
                follow(follower, toEnd())
                //During path, turn off transfer and shooter and all mechanisms besides the path

        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        //follower = Constants.create(hardwareMap);
        depollenator.initDepollenator(hardwareMap);
        intakeTransfer.initTransfer(hardwareMap);
        transferStopper.initTransferStopper(hardwareMap);
        adjustableHood.initHoodServo(hardwareMap);
        outtake.initOuttake(hardwareMap);

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

    public Path shootAndToFlower1() {
        return Paths.line(start, shootAndToFlower1).linear(start, shootAndToFlower1);
    }

    public Path intakeFlower1() {
        return Paths.line(shootAndToFlower1, intakeFlower1).constant(intakeFlower1);
    }

    public Path backUpFromFlower1() {
        return Paths.line(intakeFlower1, backUpFromFlower1).constant(backUpFromFlower1);
    }

    public Path toShootTip2() {
        return Paths.curve(toShootTip2Start, toShootTip2Control1, toShootTip2Control2, toShootTip2).linear(toShootTip2Start, toShootTip2);
    }

    public Path toIntakeFlower2() {
        return Paths.curve(toShootTip2, toIntakeFlower2Control1, toIntakeFlower2).constant(toIntakeFlower2);
    }

    public Path intakeFlower2() {
        return Paths.line(toIntakeFlower2, intakeFlower2).constant(intakeFlower2);
    }

    public Path backUpFlower2() {
        return Paths.line(intakeFlower2, backUpFlower2).constant(backUpFlower2);
    }

    public Path toShoot() {
        return Paths.curve(backUpFlower2, toShootControl1, toShoot).constant(toShoot);
    }

    public Path toEnd() {
        return Paths.curve(toShoot, toEndControl1, toEnd).constant(toEnd);
    }
}
