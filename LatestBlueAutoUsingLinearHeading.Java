package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.Robot;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "BlueAutoRight")
public class BlueAutoRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot autoRobot = new Robot(hardwareMap);
        waitForStart();
        if (isStopRequested()) return;
        /*
        while (autobot.frontDistanceSensor.getDistance(DistanceUnit.INCH) > 3 ){
            autobot.driveTrainPower(0.3);
        }
        autobot.driveTrainPower(0);
        sleep(1000);
        int parkPos = autobot.detectColor();
        telemetry.addData("Color", parkPos);
        telemetry.update();
        sleep(50000);
        */

        Pose2d startPose = new Pose2d(-36, 72, Math.toRadians(270));
        autoRobot.setPoseEstimate(startPose);

        Trajectory colorDetectTraj = autoRobot.trajectoryBuilder(startPose)
                .forward(16.5)
                .build();

        autoRobot.followTrajectory(colorDetectTraj);
        sleep(1000);
        int parkValue = autoRobot.detectColor();
        telemetry.addData("Color", parkValue);
        telemetry.update();

        Trajectory moveToStick = autoRobot.trajectoryBuilder(colorDetectTraj.end())
                //.splineToSplineHeading(new Pose2d(-36, 12, Math.toRadians(330)), Math.toRadians(180))
                .lineToLinearHeading(new Pose2d(-40, 12, Math.toRadians(330)))
                //go forward pushing the Team Element out of the way; lining up to go between the sticks 12in
                //swerve right to the tall stick
                //turn towards tall stick 45ish degrees
                //forward (closing in the rest of the way)
                .build();
        autoRobot.followTrajectory(moveToStick);

        //maybe precision forward with distance sensor
        //autoRobot.liftToHeightEncoders(4,0.7);
        //autoRobot.openGrabber();
        //autoRobot.liftToHeightEncoders(0,0.7);
        /*
        if (parkValue == 1) { //Quadrant 1 x=-60 y=35
            Trajectory moveToQuadrant1Traj = autoRobot.trajectoryBuilder(moveToStick.end())
                    .splineToSplineHeading(new Pose2d(-60, 35, Math.toRadians(45)), Math.toRadians(0))//swerve left two tiles for quadrant 1
                    .build();
            autoRobot.followTrajectory(moveToQuadrant1Traj);
        } else if (parkValue == 2) { //Quadrant 2 35/35
            Trajectory moveToQuadrant2Traj = autoRobot.trajectoryBuilder(moveToStick.end())
                    .splineToSplineHeading(new Pose2d(-35, 35, Math.toRadians(45)), Math.toRadians(0))//swerve left one tile for quadrant 2
                    .build();
            autoRobot.followTrajectory(moveToQuadrant2Traj);
        } else if (parkValue == 3) { //Quadrant 3(already there) 35/13 inch
            Trajectory moveToQuadrant3Traj = autoRobot.trajectoryBuilder(moveToStick.end())//stay and do something like: System.out.println("celebrate!");
                    .splineToSplineHeading(new Pose2d(-13, 35, Math.toRadians(45)), Math.toRadians(0))
                    .build();
            autoRobot.followTrajectory(moveToQuadrant3Traj);
        }
        */
    }
}
