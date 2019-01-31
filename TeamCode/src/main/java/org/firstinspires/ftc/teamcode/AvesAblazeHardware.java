package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;

/**
 * Created by Rohan Mathur on 9/17/18.
 */
public class AvesAblazeHardware {
	public VuforiaLocalizer.Parameters parameters;
	DcMotor motor0;
	DcMotor motor1;
	DcMotor motor2;
	DcMotor motor3;

	public DcMotor lift1;
	public DcMotor lift2;

	DcMotor arm;
	DcMotor extension;

	BNO055IMU imu1;
	BNO055IMU imu;
	int startingHeight;

	Servo marker1;

	Servo lid;

	Servo phoneServoX;
	Servo phoneServoY;

	RevBlinkinLedDriver lights;
	// We will define some constants and conversions here
	public static final float mmPerInch        = 25.4f;
	public static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
	public static final float mmTargetHeight   = (6) * mmPerInch;
	public static double angle=0;
	int startingAngle=0;
	int startingExtension=0;
	double correction;
	// the height of the center of the target image above the floor
	VuforiaTrackables targetsRoverRuckus;
	List<VuforiaTrackable> allTrackables;
	VuforiaTrackable currentTrackable;
	// Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
	// Valid choices are:  BACK or FRONT
	public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

	public OpenGLMatrix lastLocation = null;
	public boolean targetVisible = false;

	public Orientation rotation;
	public VectorF translation;
	Orientation angles;

	HardwareMap hwMap;

	/* Initialize standard Hardware interfaces */
	public void init(HardwareMap ahwMap) {

		//Getting servos for HardwareMap
		hwMap=ahwMap;
		marker1=hwMap.get(Servo.class, "marker1");
		extension=hwMap.get(DcMotor.class, "extension");
		lid=hwMap.get(Servo.class, "lid");
		lid.setPosition(0.85);
		marker1.setPosition(1);
		lights=(RevBlinkinLedDriver)hwMap.get("lights");


		//Getting sensors from HardwareMap
		imu1=hwMap.get(BNO055IMU.class, "imu 1");
		imu=hwMap.get(BNO055IMU.class, "imu");
		/*
			MOTORS AT FULL POWER ALL MOVING FORWARD MOVE AT 2.618 ft/sec
		*/

		//Getting motors from HardwareMap and initializing them
		//names self explanatory


		/*  \\0     1//
			|		  |

			|		  |

			|		  |
			//2		3\\
		*/


		motor0 = hwMap.get(DcMotor.class, "motor0");
		motor0.setDirection(DcMotor.Direction.FORWARD);
		motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor1 = hwMap.get(DcMotor.class, "motor1");
		motor1.setDirection(DcMotor.Direction.FORWARD);
		motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor2 = hwMap.get(DcMotor.class, "motor2");
		motor2.setDirection(DcMotor.Direction.FORWARD);
		motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor3 = hwMap.get(DcMotor.class, "motor3");
		motor3.setDirection(DcMotor.Direction.FORWARD);
		motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		lift1 = hwMap.get(DcMotor.class, "lift1");
		lift1.setDirection(DcMotor.Direction.FORWARD);
		lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		lift2 = hwMap.get(DcMotor.class, "lift2");
		lift2.setDirection(DcMotor.Direction.REVERSE);
		lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		extension.setDirection(DcMotor.Direction.REVERSE);

		arm=hwMap.get(DcMotor.class, "arm");
		lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		startingHeight=lift1.getCurrentPosition();

		//int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
		//parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

		startingExtension=extension.getCurrentPosition();

		phoneServoX = hwMap.get(Servo.class, "phoneServoX");
		phoneServoY = hwMap.get(Servo.class, "phoneServoY");

		phoneServoX.setPosition(0.5);
		phoneServoY.setPosition(0.5);
	}





}
