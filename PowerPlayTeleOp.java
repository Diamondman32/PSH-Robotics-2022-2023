package org.firstinspires.ftc.teamcode.drive.opmode;
import org.firstinspires.ftc.teamcode.drive.Robot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.lang.Math;

@TeleOp(name="Power Play TeleOp",group=" ")
public class PowerPlayTeleOp extends OpMode {

    private Robot teleRobot;
    public void init() {
        teleRobot = new Robot (hardwareMap);
    }

    public void loop(){
        //CONTROLLER A (Driving Controller)
        //Drive train and half speed
        float yPosLeft01 = gamepad1.left_stick_y;
        float yPosRight01 = gamepad1.right_stick_y;
        boolean rightBumper1 = gamepad1.right_bumper;
        boolean leftBumper1 = gamepad1.left_bumper;

        if(rightBumper1) {
            teleRobot.leftFront.setPower(0.75);
            teleRobot.rightFront.setPower(-0.75);
            teleRobot.leftRear.setPower(-0.75);
            teleRobot.rightRear.setPower(0.75);
        } else if(leftBumper1) {
            teleRobot.leftFront.setPower(-0.75);
            teleRobot.rightFront.setPower(0.75);
            teleRobot.leftRear.setPower(0.75);
            teleRobot.rightRear.setPower(-0.75);
        } else {
            teleRobot.leftFront.setPower(-yPosLeft01);
            teleRobot.leftRear.setPower(-yPosLeft01);
            teleRobot.rightFront.setPower(-yPosRight01);
            teleRobot.rightRear.setPower(-yPosRight01);
        }

        //Auto Lift
        if (gamepad1.a){
            teleRobot.liftToHeightEncoders(1, 0.4);
        }
        if (gamepad1.b){
            teleRobot.liftToHeightEncoders(2, 0.4);
        }
        if (gamepad1.y){
            teleRobot.liftToHeightEncoders(3, 0.4);
        }


        //CONTROLLER B (Mechanism Controller)


        //Manual Lift
        float liftPower = gamepad2.left_stick_y;
        teleRobot.liftMotor.setPower(liftPower*2);

        //Grabber Code
        if (gamepad2.right_bumper){
            //open
            teleRobot.grabber.setPosition(0.65);
        }

        if (gamepad2.left_bumper){
            //closed
            teleRobot.grabber.setPosition(1);
        }

        telemetry.addData("Red  ", teleRobot.colorSensor.red());
        telemetry.addData("Green", teleRobot.colorSensor.green());
        telemetry.addData("Blue ", teleRobot.colorSensor.blue());
        telemetry.addData("Color", teleRobot.detectColor());
        telemetry.update();
    }
}
