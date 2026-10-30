package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

//Although this method is the one I coded for, implement this method once the bot is one and the limelight is mounted: Look this up: "tangent method to get distance from limelight 2d is it better than area regression"
@TeleOp
public class DistanceWithTanMethod extends OpMode {
    private Limelight3A limelight3A;

    private double distance;
    private IMU imu;
    IMU.Parameters parameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
    );

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(parameters);
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(0);
    }

    @Override
    public void start() {
        limelight3A.start();
    }

    @Override
    public void loop() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        double heading = orientation.getYaw(AngleUnit.DEGREES);

        LLResult llResult = limelight3A.getLatestResult();
        if (llResult != null && llResult.isValid()) {


            double targetOffsetAngle_Vertical = llResult.getTy();

            // how many degrees back is your limelight rotated from perfectly vertical?
            double limelightMountAngleDegrees = 44.633;

            // distance from the center of the Limelight lens to the floor
            //260.938mm
            double limelightLensHeightInches = 10.273;

            // distance from the target to the floor
            //1247.5379999914mm according to CAD, but might be inaccurate, so did .2 higher based on physical high a little
            double goalHeightInches = 49.3;

            double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
            double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

            //calculate distance
            double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

            telemetry.addData("Target Y: ", llResult.getTy());
            telemetry.addData("Distance: ", distanceFromLimelightToGoalInches);
        }
    }

}
