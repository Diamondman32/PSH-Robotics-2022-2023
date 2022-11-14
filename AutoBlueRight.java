package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.robot.Robot;
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
public class AutoBlueRight extends LinearOpMode {
    private robot autoRobot;
    public void runOpMode() throws InterruptedException {
        autoRobot = new robot (this);
        waitForStart();

    //use color sensor to detect color
        autoRobot.driveDistance(-3, 0.3); //drive close 18in
        int parkValue = autoRobot.detectColor();
    //go to tall stick
        autoRobot.driveDistance(-3, 0.3); //pushing the Team Element out of the way 12in
        autoRobot.rightSwerveDistance(-3, 0.3);
        autoRobot.turnDeg(45, 0.5);
        //Cap
        autoRobot.driveDistance(-3, 0.3); //forward (closing in the rest of the way)
        autoRobot.liftToHeightEncoders(4,0.7);
        autoRobot.openGrabber();
    //Park
        autoRobot.driveDistance(-3, 0.3); //back up
        autoRobot.liftToHeightEncoders(0,0.7);
        autoRobot.turnDeg(-45, 0.5); //turnLeft 45 degrees
        //depending on color sensor, go forward to parking
        if (parkValue == 1) { //Quadrant 1
            autoRobot.driveDistance(-3, 0.3);
        } else if (parkValue == 2) { //Quadrant 2
            autoRobot.driveDistance(-3, 0.3);
        } else if (parkValue == 3) { //Quadrant 3(already there)
            //celebrate
        }
    }
}
