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
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

	Orientation angles;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		telemetry.addData("status", "ready");
		telemetry.update();
		liftHeight=robot.getLiftHeight();
		waitForStart();
		robot.rotateToAngle(180);

		while(opModeIsActive()){
			telemetry.addData("angle", robot.getAngle());
			telemetry.update();
		}
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
		while((Math.abs(robot.rotation.thirdAngle)<179|!robot.resetCoordinates())&&opModeIsActive()){
			robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("angle", robot.rotation.thirdAngle);
			telemetry.update();

			if(Math.abs(robot.rotation.thirdAngle)<170){
				direction=1;
			}

			if(Math.abs(robot.rotation.thirdAngle)>165){
				robot.rotate(-0.05*direction);
			}
			else if(Math.abs(robot.rotation.thirdAngle)<90){
				robot.rotate(-0.3*direction);
			}
			else{
				robot.rotate(-0.1*direction);
			}

			}
			robot.stopMotors();
		sleep(1000);
		robot.moveUpDown(0.15);
		robot.translation=robot.lastLocation.getTranslation();
		while(robot.resetCoordinates()&&opModeIsActive()&&Math.abs(Math.abs(robot.getY())+Math.abs(robot.getX()))<59){
			robot.moveUpDown(0.15);
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", robot.getX());
			telemetry.addData("y", robot.getY());
			telemetry.update();
			while(gamepad1.a){
				robot.stopMotors();
			}
			sleep(500);
			while(gamepad1.a){
				robot.stopMotors();
			}
			robot.stopMotors();
			sleep(500);
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", robot.translation.get(0));
			telemetry.addData("y", robot.translation.get(1));
			telemetry.update();
			while(gamepad1.a){
				robot.stopMotors();
			}
		}
		robot.stopMotors();
		sleep(1000);
		//robot.moveLeftRight(0.8);
		sleep(200);
		while(robot.getX()<-52&&opModeIsActive()){
			robot.moveLeftRight(0.2);
		}
		robot.stopMotors();
		sleep(1000);
		direction=-1;
		while((Math.abs(robot.rotation.thirdAngle)<179|!robot.resetCoordinates())&&opModeIsActive()){
			robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("angle", robot.rotation.thirdAngle);
			telemetry.update();

			if(Math.abs(robot.rotation.thirdAngle)<170){
				direction=1;
			}

			if(Math.abs(robot.rotation.thirdAngle)>165){
				robot.rotate(-0.05*direction);
			}
			else if(Math.abs(robot.rotation.thirdAngle)<90){
				robot.rotate(-0.3*direction);
			}
			else{
				robot.rotate(-0.1*direction);
			}

		}
		BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
		imuParameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
		imuParameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
		imuParameters.loggingEnabled      = true;
		imuParameters.loggingTag          = "IMU";
		imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
		robot.imu1.initialize(imuParameters);
		//robot.moveLeftRight(0.8);
		sleep(200);
		while(robot.getX()<-43&&opModeIsActive()){
			robot.moveLeftRight(0.2);
		}
		robot.stopMotors();
		sleep(1000);
		direction=-1;
		/*while((Math.abs(robot.rotation.thirdAngle)<179|!robot.resetCoordinates())&&opModeIsActive()){
			robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("angle", robot.rotation.thirdAngle);
			telemetry.update();

			if(Math.abs(robot.rotation.thirdAngle)<170){
				direction=1;
			}

			if(Math.abs(robot.rotation.thirdAngle)>165){
				robot.rotate(-0.05*direction);
			}
			else if(Math.abs(robot.rotation.thirdAngle)<90){
				robot.rotate(-0.3*direction);
			}
			else{
				robot.rotate(-0.1*direction);
			}

		}*/
		//robot.moveLeftRight(0.8);
		sleep(200);
		while(robot.getX()<-32&&opModeIsActive()&&robot.resetCoordinates()){
			robot.moveLeftRight(0.2);
		}
		if(!robot.resetCoordinates()){
			robot.moveLeftRight(0.2);
			sleep(100);
		}
		robot.stopMotors();
		sleep(1000);direction=-1;
		/*while((Math.abs(robot.rotation.thirdAngle)<179|!robot.resetCoordinates())&&opModeIsActive()){
			robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("angle", robot.rotation.thirdAngle);
			telemetry.update();

			if(Math.abs(robot.rotation.thirdAngle)<170){
				direction=1;
			}

			if(Math.abs(robot.rotation.thirdAngle)>165){
				robot.rotate(-0.05*direction);
			}
			else if(Math.abs(robot.rotation.thirdAngle)<90){
				robot.rotate(-0.3*direction);
			}
			else{
				robot.rotate(-0.1*direction);
			}

		}*/
		robot.moveUpDown(1);
		sleep(700);
		robot.stopMotors();

		robot.rotate(-1);

		do {
			 angles   = robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			telemetry.addData("angle", angles.firstAngle);
			if(angles.firstAngle>25){
				robot.rotate(-0.3);
			}
			if(angles.firstAngle>35){
				robot.rotate(-0.1);
			}
		}
		while((angles.firstAngle<44||angles.firstAngle>46)&&opModeIsActive());

		robot.moveUpDown(0.5);
		sleep(800);
		robot.stopMotors();
		robot.marker1.setPosition(0.3);
		sleep(3000);
		robot.marker1.setPosition(1);
		robot.rotate(-1);
		do {
			angles   = robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			telemetry.addData("angle", angles.firstAngle);
			if(angles.firstAngle>115){
				robot.rotate(-0.3);
			}
			if(angles.firstAngle>125){
				robot.rotate(-0.1);
			}
		}
		while((angles.firstAngle<134||angles.firstAngle>136)&&opModeIsActive());
		robot.moveUpDown(1);
		sleep(2300);
		robot.stopMotors();

		while (opModeIsActive()){
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
