package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="Deploy", group="Pushbot")

public class Deploy extends LinearOpMode {

	/* Declare OpMode members. */

	AvesAblazeHardwarePushbot robot   = new AvesAblazeHardwarePushbot();   // Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	int liftHeight;
	double sd;
	String color;
	Orientation angles;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		sleep(1);
		while(Math.abs(liftHeight-robot.getLiftHeight())<3450&&!gamepad1.a){
			robot.lift("up");
			telemetry.clearAll();
			telemetry.addData("liftHeight", robot.getLiftHeight());
			telemetry.addData("startingHeight", liftHeight);
			telemetry.update();
		}
		robot.lift("stop");
		sleep(100);
		robot.moveLeftRight(-0.75);
		sleep(250);
		robot.moveUpDown(1);
		sleep(100);
		robot.stopMotors();
		sleep(100);
		liftHeight=robot.getLiftHeight();
		while(Math.abs(liftHeight-robot.getLiftHeight())<3300&&!gamepad1.a){
			robot.lift("down");
		}
		robot.lift("stop");
		robot.moveUpDown(1);
		sleep(200);
		robot.stopMotors();
		sleep(500);
		robot.moveLeftRight(-0.3);
		sleep(1500);
		robot.rotate(0.1);
		sleep(200);
		while(!robot.resetCoordinates()&&opModeIsActive()){
			robot.rotate(-0.1);
			sleep(200);
			robot.stopMotors();
			sleep(200);
		}
		robot.stopMotors();
		telemetry.clearAll();
		robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		telemetry.addData("angle", robot.rotation.thirdAngle);
		telemetry.update();
		sleep(500);
		robot.rotate(-0.1);
		robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		int direction=-1;
			robot.stopMotors();
		sleep(1000);
		robot.moveUpDown(0.15);
		robot.translation=robot.lastLocation.getTranslation();
		robot.rotateToAngle(135);
		BNO055IMU.Parameters imuParameters;
		imuParameters = new BNO055IMU.Parameters();
		imuParameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
		imuParameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
		imuParameters.loggingEnabled      = true;
		imuParameters.loggingTag          = "IMU";
		imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
		robot.imu1.initialize(imuParameters);
		robot.startingAngle=135;
		while(robot.resetCoordinates()&&opModeIsActive()&&Math.abs(Math.abs(robot.getY())+Math.abs(robot.getX()))<57){
			robot.moveUpDown(0.1);
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", robot.getX());
			telemetry.addData("y", robot.getY());
			telemetry.update();
			while(gamepad1.a){
				robot.stopMotors();
			}
			while(gamepad1.a){
				robot.stopMotors();
			}
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", robot.translation.get(0));
			telemetry.addData("y", robot.translation.get(1));
			telemetry.update();
			while(gamepad1.a){
				robot.stopMotors();
			}
		}
		robot.rotateToAngle(135);
		robot.stopMotors();
		sleep(1000);
		//robot.moveLeftRight(0.8);
		sleep(200);
		while(!(robot.sensorDistance.getDistance(DistanceUnit.INCH)<20)&&opModeIsActive()&&robot.resetCoordinates()){
			robot.moveLeftRight(0.2);
		}
		sleep(150);
		robot.stopMotors();
		color=robot.checkColor();
		sleep(1000);
		robot.rotateToAngle(135);
		while(opModeIsActive()&&!gamepad1.a){
			telemetry.update();
			telemetry.addData("ratio", robot.sensorColor.blue() / Math.pow(20 - robot.sensorDistance.getDistance(DistanceUnit.INCH), 1));
		}
		//robot.moveLeftRight(0.8);
		sleep(200);
		if(color.equals("yellow")){
			robot.drive(23,true,0.8);
			sleep(700);
			robot.stopMotors();
			sleep(1500);
			robot.rotateToAngle(180);
			robot.moveLeftRight(0.5);
			sleep(600);
			robot.stopMotors();
			robot.marker1.setPosition(0.3);
			sleep(1000);
			robot.marker1.setPosition(1);
			robot.stopMotors();
		}
		else {
			robot.moveLeftRight(0.2);
			sleep(1000);
			while (!(robot.sensorDistance.getDistance(DistanceUnit.INCH) < 20) && opModeIsActive() && robot.resetCoordinates()) {
				robot.moveLeftRight(0.2);
				telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
				telemetry.update();
			}
			sleep(150);
			robot.rotateToAngle(135);
			robot.stopMotors();
			sleep(1000);
			sleep(200);
			if(color.equals("yellow")){
				robot.drive(29,true,0.8);
				robot.stopMotors();
				sleep(1500);
				robot.rotateToAngle(180);
				robot.drive(6, true,0.8);
				robot.marker1.setPosition(0.3);
				sleep(1000);
				robot.marker1.setPosition(1);
			}
			else {
				robot.moveLeftRight(0.2);
				sleep(1000);

				while (!(robot.sensorDistance.getDistance(DistanceUnit.INCH) < 20) && opModeIsActive() && robot.resetCoordinates()) {
					robot.moveLeftRight(0.2);
					telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
					telemetry.update();
				}
				robot.stopMotors();
				robot.drive(21, true,0.8);
				robot.stopMotors();
				sleep(1500);
				robot.rotateToAngle(180);
				robot.drive(23, true,0.8);
				robot.marker1.setPosition(0.3);
				sleep(1000);
				robot.marker1.setPosition(1);
			}


			robot.stopMotors();
			color = "hi";

		}
		robot.rotateToAngle(265);
		robot.drive(83, true,0.8);
		robot.stopMotors();
		while (opModeIsActive()){
			telemetry.addData("color", color);
			telemetry.update();
			 angles   = robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			telemetry.addData("angle", angles.firstAngle);
			telemetry.addData("height", robot.getLiftHeight());
			//Display coordinates and trackable
			if (robot.resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				robot.translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", robot.getX());
				telemetry.addData("y", robot.getY());

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", robot.rotation.thirdAngle);
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}
	public void printStuff(){
		if (robot.resetCoordinates()) {
			telemetry.addData("Target", robot.currentTrackable.getName());
			// express position (translation) of robot in inches.
			robot.translation = robot.lastLocation.getTranslation();
			//ArrayList translation[x, y, z]
			telemetry.addData("x", robot.getX());
			telemetry.addData("y", robot.getY());

			// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
			robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("Heading", robot.getAngle());
		} else {
			telemetry.addData("Target", "none");
		}
	}



}
