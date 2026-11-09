package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "FlowerIntakeTwoTipAuto", group = "Autonomous")
public class FlowerIntakeTwoTipAutoPath extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(57, 9, 270);
    private final Pose shootAndToFlower1 = poseFactory.of(28.3104, 46.6007, 180);
    private final Pose intakeFlower1 = poseFactory.of(15.9789, 46.4795, 180);
    private final Pose backUpFromFlower1 = poseFactory.of(22.7824, 46.452, 180);
    private final Pose toFlower2Start = poseFactory.of(22.7824, 46.452, 180);
    private final Pose toFlower2 = poseFactory.of(46.6736, 112.0576, 90);
    private final Pose toFlower2Control1 = poseFactory.of(22.8447, 108.5307, 0);
    private final Pose intakeFlower2 = poseFactory.of(46.9481, 124.3554, 90);
    private final Pose backUpFromFlower2 = poseFactory.of(46.8891, 115.4321, 90);
    private final Pose toShootFlower2 = poseFactory.of(60.5315, 133.2368, 90);
    private final Pose toShootFlower2Control1 = poseFactory.of(58.091, 121.1228, 0);
    private final Pose toPark = poseFactory.of(16.8873, 116.6943, 90);
    private final Pose toParkControl1 = poseFactory.of(53.3913, 106.4292, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, shootAndToFlower1()),
                follow(follower, intakeFlower1()),
                follow(follower, backUpFromFlower1()),
                follow(follower, toFlower2()),
                follow(follower, intakeFlower2()),
                follow(follower, backUpFromFlower2()),
                follow(follower, toShootFlower2()),
                follow(follower, toPark())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
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

    public Path toFlower2() {
        return Paths.curve(toFlower2Start, toFlower2Control1, toFlower2).linear(toFlower2Start, toFlower2);
    }

    public Path intakeFlower2() {
        return Paths.line(toFlower2, intakeFlower2).constant(intakeFlower2);
    }

    public Path backUpFromFlower2() {
        return Paths.line(intakeFlower2, backUpFromFlower2).constant(backUpFromFlower2);
    }

    public Path toShootFlower2() {
        return Paths.curve(backUpFromFlower2, toShootFlower2Control1, toShootFlower2).constant(toShootFlower2);
    }

    public Path toPark() {
        return Paths.curve(toShootFlower2, toParkControl1, toPark).constant(toPark);
    }
}
