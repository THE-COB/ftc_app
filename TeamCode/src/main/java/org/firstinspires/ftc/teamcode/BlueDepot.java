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

import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="BlueDepot", group="Pushbot")

public class BlueDepot extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	// Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	int liftHeight = 0;
	double sd;
	String color;
	Orientation angles;
	@Override
	public void runOpMode() {
		telemetry.addData("status", "calibrating");
		telemetry.update();
		robot.init(hardwareMap);
		telemetry.clearAll();
		telemetry.addData("status", "ready");
		telemetry.update();
			waitForStart();
		//Deploys the robot down from when it is at the starting position
		while(Math.abs(liftHeight-getLiftHeight())<3760&&!gamepad1.a&&opModeIsActive()){
			lift("up");
			telemetry.clearAll();
			telemetry.addData("liftHeight", getLiftHeight());
			telemetry.addData("startingHeight", liftHeight);
			telemetry.update();
		}
		lift("stop");

		//Once the robot reaches the floor it moves to the left and goes up. It then resets the linear slide
		moveLeftRight(-0.75);
		try{
			sleep(250);
		}
		catch(Exception e)

	{
		stopMotors();
		}
		moveUpDown(1);
		try{
			sleep(100);
		}
		catch(Exception e){

			stopMotors();
		}
		stopMotors();
		liftHeight=getLiftHeight();
		lift("down");
		moveUpDown(1);
		try{
			sleep(300);
		}
		catch(Exception e){
			stopMotors();
		}
		stopMotors();
		while(Math.abs(liftHeight-getLiftHeight())<3000&&!gamepad1.a&&opModeIsActive()){
			lift("down");
		}
		lift("stop");

		stopMotors();
		moveLeftRight(-1);
		try{
			sleep(600);
		}
		catch(Exception e){
			stopMotors();
		}
		//If the robot is unable to find the Vuforia it continues to rotate
		while(!resetCoordinates()&&opModeIsActive()){
			rotate(-0.1);
			try{
				sleep(200);
			}
			catch(Exception e){
				stopMotors();
			}
			stopMotors();
			try{
				sleep(200);
			}
			catch(Exception e){
				stopMotors();
			}
		}
		stopMotors();
		telemetry.clearAll();

		//Uses information from Vuforia and prints it and sets variables to use Vuforia information and calibrates the REV imu
		robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		robot.translation=robot.lastLocation.getTranslation();
		rotateToAngle(135);
		calibrate();
		while(!robot.imu1.isGyroCalibrated()&&opModeIsActive());
		robot.startingAngle=135;
		resetCoordinates();
		robot.translation=robot.lastLocation.getTranslation();
		telemetry.addData("x", getX());
		telemetry.update();
		while(resetCoordinates()&&opModeIsActive()&&Math.abs(Math.abs(getY())+Math.abs(getX()))<58){
			moveUpDown(0.1);
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", getX());
			telemetry.addData("y",getY());
			telemetry.update();
		}
		stopMotors();
		rotateToAngle(135);

		Callable<Boolean> checkDistance=new Callable<Boolean>() {
			public Boolean call() {
				return !(robot.sensorDistance.getDistance(DistanceUnit.INCH)<20)&&opModeIsActive()&&resetCoordinates();
			}
		};

		//Reads color of the left material
		moveLeftRight(0.3, checkDistance);
		telemetry.addData("color",checkColor());
		try{
			sleep(1000);
		}
		catch(Exception e){
			stopMotors();
		}
		color=checkColor();
		rotateToAngle(135);
		//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.
		if(color.equals("yellow")){
			telemetry.addData("Gold", "left");
			telemetry.update();
			moveLeftRight(-0.1);
			try{
				sleep(350);
			}
			catch(Exception e){
				stopMotors();
			}
			drive(23,true,0.6);
			try{
				sleep(700);
			}
			catch(Exception e){
				stopMotors();
			}
			stopMotors();
			try{
				sleep(1500);
			}
			catch(Exception e){
				stopMotors();
			}
			rotateToAngle(180);
			moveLeftRight(0.5);
			try{
				sleep(600);
			}
			catch(Exception e){
				stopMotors();
			}
			stopMotors();
			robot.marker1.setPosition(0.3);
			try{
				sleep(1000);
			}
			catch(Exception e){
				stopMotors();
			}
			robot.marker1.setPosition(1);
			stopMotors();
		}
		//If the mineral is not gold it will read color of the center mineral
		else {
			moveLeftRight(0.1);
			try{
				sleep(500);
			}
			catch(Exception e){
				stopMotors();
			}
				moveLeftRight(0.1, checkDistance);
				telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
				telemetry.update();
			try{
				sleep(1000);
			}
			catch(Exception e){
				stopMotors();
			}

			color=checkColor();
			rotateToAngle(135);

			//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.
			stopMotors();
			if(color.equals("yellow")){
				telemetry.addData("Gold", "middle");
				telemetry.update();
				moveLeftRight(0.2);
				try{
					sleep(400);
				}
				catch(Exception e){
					stopMotors();
				}
				drive(29,true,0.6);
				stopMotors();
				rotateToAngle(180);
				drive(6, true,0.8);
				robot.marker1.setPosition(0.3);
				try{
					sleep(500);
				}
				catch(Exception e){
					stopMotors();
				}
				robot.marker1.setPosition(1);
			}
			//If neither the left nor the center mineral is gold it will move to the right mineral
			else {
				telemetry.addData("Gold", "left");
				telemetry.update();
				moveLeftRight(0.3);
				try{
					sleep(400);
				}
				catch(Exception e){
					stopMotors();
				}
				moveLeftRight(0.1, checkDistance);
				moveLeftRight(0.1);
				try{
					sleep(250);
				}
				catch(Exception e){
					stopMotors();
				}
					telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
					telemetry.update();
				stopMotors();
				//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.
				drive(21, true,0.6);
				stopMotors();
				rotateToAngle(180);
				drive(23, true,0.8);
				robot.marker1.setPosition(0.3);
				try{
					sleep(500);
				}
				catch(Exception e) {
					stopMotors();
				}
				robot.marker1.setPosition(1);
			}


			stopMotors();


		}

		//While the code is running it will constantly print the color, angle, lift height, and coordinates
		rotateToAngle(265);
		drive(68, true,0.8);
		stopMotors();
		while (opModeIsActive()){
			telemetry.addData("color", color);
			telemetry.update();
			 angles   = robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			telemetry.addData("angle", angles.firstAngle);
			telemetry.addData("height", getLiftHeight());
			//Display coordinates and trackable
			if (resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				robot.translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", getX());
				telemetry.addData("y", getY());

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", robot.rotation.thirdAngle);
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}



}
