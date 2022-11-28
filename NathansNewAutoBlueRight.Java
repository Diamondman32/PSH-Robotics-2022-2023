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
        Robot autobot = new Robot(hardwareMap);
        waitForStart();
        if (isStopRequested()) return;

        while (autobot.frontDistanceSensor.getDistance(DistanceUnit.INCH) > 3 ){
            autobot.driveTrainPower(0.3);
        }
        autobot.driveTrainPower(0);
        sleep(1000);
        int parkPos = autobot.detectColor();
        telemetry.addData("Color", parkPos);
        telemetry.update();
        sleep(50000);
        /*
        Pose2d startPose = new Pose2d(10, 15, Math.toRadians(90));

        Trajectory traj = autobot.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(30, 30), 0)
                .build();

        autobot.followTrajectory(traj);

        sleep(2000);

        autobot.followTrajectory(
                autobot.trajectoryBuilder(traj.end(), true)
                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
                        .build()
        );*/
    }
}
