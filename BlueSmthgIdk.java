package org.firstinspires.ftc.teamcode.drive.opmode.personal;
import org.firstinspires.ftc.teamcode.drive.Robot;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Auto Blue Right", group = "")
public class BlueSmthgIdk extends LinearOpMode {

    private Robot autoRobot;
    public void runOpMode() throws InterruptedException {
        autoRobot = new Robot(hardwareMap);
        waitForStart();

        autoRobot.setPoseEstimate(new Pose2d());

        Trajectory colorDetectTraj = autoRobot.trajectoryBuilder(new Pose2d())

                .build();
        int parkValue = autoRobot.detectColor();
        Trajectory MoveToStick = autoRobot.trajectoryBuilder(new Pose2d())
                //go forward pushing the Team Element out of the way; lining up to go between the sticks 12in
                //swerve right to the tall stick
                //turn towards tall stick 45ish degrees
                //forward (closing in the rest of the way)
                .build();

        Trajectory CappingTraj = autoRobot.trajectoryBuilder(new Pose2d())
                .liftToHeightEncoders(4,0.7);
                .openGrabber();
                .liftToHeightEncoders(0,0.7);
                .build();

        Trajectory parkingTraj = autoRobot.trajectoryBuilder(new Pose2d())

                .build();
            //back up
            //turnLeft 45 degrees
            //depending on color sensor, go forward to parking
            if (parkValue == 1) { //Quadrant 1
                Trajectory moveToQuadrant2Traj = autoRobot.trajectoryBuilder(new Pose2d())
                        //swerve left two tiles for quadrant 1
                        .build();
            } else if (parkValue == 2) { //Quadrant 2
                Trajectory moveToQuadrant2Traj = autoRobot.trajectoryBuilder(new Pose2d())
                        //swerve left one tile for quadrant 2
                        .build();
            } else if (parkValue == 3) { //Quadrant 3(already there)
                //stay and do something like: System.out.println("celebrate!");
            }
    }
}
