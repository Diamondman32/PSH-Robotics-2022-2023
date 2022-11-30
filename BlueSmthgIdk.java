package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.Robot;

@Autonomous(name = "BlueAutoRight")
public class BlueSmthgIdk extends LinearOpMode {

    //Cannot say new Pose2d for every trajectories start value. Instead, [previous Trajectory].end()
    //.spineTo(new Vector2d(x1, y1), heading) for movement in Trajectories.
    //Ex: .splineTo(new Vector2d(20, 9), Math.toRadians(45))
    // .turn(heading); cannot be used in trajectories
    //If you turn outside a trajectory, the .end statement will be incorrect so you should do something like: traj1.end().plus(new Pose2d(0, 0, Math.toRadians(90)))
    //go backwards in a trajectory by adding a true after new Pose2d()
    //ex: Trajectory trajectory = drive.trajectoryBuilder(new Pose2d(), true)

    private Robot autoRobot;
    public void runOpMode() throws InterruptedException {
        autoRobot = new Robot(hardwareMap);
        waitForStart();
        if (isStopRequested()) return;

        while (autoRobot.frontDistanceSensor.getDistance(DistanceUnit.INCH) > 3 ){
            autoRobot.driveTrainPower(0.3);
        }
        autoRobot.driveTrainPower(0);
        sleep(1000);
        int parkPos = autoRobot.detectColor();
        telemetry.addData("Color", parkPos);
        telemetry.update();
        sleep(50000);

        autoRobot.setPoseEstimate(new Pose2d());
        //NOT BEING USED
        //COLOR DETECTION
        /*Trajectory colorDetectTraj = autoRobot.trajectoryBuilder(new Pose2d())
                .forward(15)
                .build();*/

        int parkValue = autoRobot.detectColor();


        //MOVE TO STICK x=-8 y=29 inches
        Trajectory moveToStick = autoRobot.trajectoryBuilder(new Pose2d())
                .splineToSplineHeading(new Pose2d(-8, 29, Math.toRadians(45)), Math.toRadians(0))//coordinates are wrong
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

        //back up
        //turnLeft 45 degrees
        //depending on color sensor, go forward to parking
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
    }
}
