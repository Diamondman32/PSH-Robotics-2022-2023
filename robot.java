package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class robot {
    //Variable Instantiation 
    public DcMotor leftFrontMotor;
    public DcMotor rightFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightBackMotor;
    public Servo grabber;
    public DcMotor liftMotor;
    public BNO055IMU imu;
    public OpMode opmode;
    public ColorSensor colorSensor;

    //Initialization
    public robot(OpMode opmode) {
        //Storing OpMode
        this.opmode = opmode;

        //Drive train motors
        leftFrontMotor = opmode.hardwareMap.dcMotor.get("front_left_motor");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftBackMotor = opmode.hardwareMap.dcMotor.get("back_left_motor");
        leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFrontMotor = opmode.hardwareMap.dcMotor.get("front_right_motor");
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBackMotor = opmode.hardwareMap.dcMotor.get("back_right_motor");
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        
        //Lift Motor
        liftMotor = opmode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        

        //Servos
        //Grabber
        grabber = opmode.hardwareMap.servo.get("grabber");

        //IMU Parameters
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;
        
        //IMU
        imu = opmode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        
        //Color Sensor
        colorSensor = opmode.hardwareMap.get(ColorSensor.class, "sensor_color");
    }

    //Robot Movement Functions for Autonomous 
    //Turns a given amount of degrees using the gyro
    public void turnDeg(int degrees, double power) throws InterruptedException {
        if (degrees >  imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle){
            while (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle < degrees) {
                opmode.telemetry.addData("Current Orientation",imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle);
                opmode.telemetry.update();
                leftFrontMotor.setPower(power);
                leftBackMotor.setPower(power);
                rightFrontMotor.setPower(-power);
                rightBackMotor.setPower(-power);
            }
        } else if (degrees <  imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle) {
            while (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle > degrees) {
                opmode.telemetry.addData("Current Orientation",imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES).firstAngle);
                opmode.telemetry.update();
                leftFrontMotor.setPower(-power);
                leftBackMotor.setPower(-power);
                rightFrontMotor.setPower(power);
                rightBackMotor.setPower(power);
            }
        }
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        Thread.sleep(1000);
    }

    //Uses encoder inputs to drive forward
    public void driveDistance(int distanceIN, double power) throws InterruptedException {
        double ticksPerRev = 560;
        //1120 for 40:1 (28 counts per revolution)
        //https://docs.revrobotics.com/15mm/actuators/motors/hd-hex-motor
        double inPerRev = Math.PI * 3.5;
        //3.5 is wheel diameter in inches
        double ticksPerInch = ticksPerRev / inPerRev;
        double ticksDistance = ticksPerInch * distanceIN;
        double startPosition = leftFrontMotor.getCurrentPosition();
        if (distanceIN < 0) power = -power;

        while (Math.abs((leftFrontMotor.getCurrentPosition()- startPosition)) < Math.abs(ticksDistance)) {
            leftFrontMotor.setPower(power);
            leftBackMotor.setPower(power);
            rightFrontMotor.setPower(power);
            rightBackMotor.setPower(power);
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        Thread.sleep(1000);
    }
    
    public void rightSwerveDistance(int distanceIN, double power) throws InterruptedException {
        double ticksPerRev = 560;
        //1120 for 40:1 (28 counts per revolution)
        //https://docs.revrobotics.com/15mm/actuators/motors/hd-hex-motor
        double inPerRev = Math.PI * 3.5;
        //3.5 is wheel diameter in inches
        double ticksPerInch = ticksPerRev / inPerRev;
        double ticksDistance = ticksPerInch * distanceIN;
        double startPosition = leftFrontMotor.getCurrentPosition();
        if (distanceIN < 0) power = -power;

        while (Math.abs((leftFrontMotor.getCurrentPosition()- startPosition)) < Math.abs(ticksDistance)) {
            leftFrontMotor.setPower(power);
            leftBackMotor.setPower(-power);
            rightFrontMotor.setPower(-power);
            rightBackMotor.setPower(power);
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        Thread.sleep(1000);
    }
    
    public void leftSwerveDistance(int distanceIN, double power) throws InterruptedException {
        double ticksPerRev = 560;
        //1120 for 40:1 (28 counts per revolution)
        //https://docs.revrobotics.com/15mm/actuators/motors/hd-hex-motor
        double inPerRev = Math.PI * 3.5;
        //3.5 is wheel diameter in inches
        double ticksPerInch = ticksPerRev / inPerRev;
        double ticksDistance = ticksPerInch * distanceIN;
        double startPosition = rightFrontMotor.getCurrentPosition();
        if (distanceIN < 0) power = -power;

        while (Math.abs((leftFrontMotor.getCurrentPosition()- startPosition)) < Math.abs(ticksDistance)) {
            leftFrontMotor.setPower(-power);
            leftBackMotor.setPower(power);
            rightFrontMotor.setPower(power);
            rightBackMotor.setPower(-power);
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        Thread.sleep(1000);
    }
    
    //Lifts the lift to a given height using encoders  
    public void liftToHeightEncoders(int level, double power){
        int liftValue = 0;
        if (level == 2) {
            liftValue = -1200;
        } else if (level == 3) {
            liftValue = -3800;
        } else if (level == 4) {
            liftValue = -4000;
        }
        
        liftMotor.setTargetPosition(liftValue);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        liftMotor.setPower(power);
         
        while (liftMotor.isBusy()){
            opmode.telemetry.addData("Lift Encoder Positon", liftMotor.getCurrentPosition());
            opmode.telemetry.update();
        }
        
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Grabber
    public void closeGrabber()  {
        grabber.setPosition(1);
    }
    
    public void openGrabber() {
        grabber.setPosition(0.65);
    }
    
    //Color Sensor
    public int detectColor() {
        int stoopid = 0; //Sam's Masterpiece
        if (colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
            stoopid = 1;
        } else if (colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
            stoopid = 2;
        } else if (colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
            stoopid = 3;
        }
        return stoopid;
    }
}
