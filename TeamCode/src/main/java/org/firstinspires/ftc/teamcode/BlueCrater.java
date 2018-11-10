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

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="BlueCrater", group="Pushbot")

public class BlueCrater extends AvesAblazeOpmode {

	/* Declare OpMode members. */

	AvesAblazeHardware robot   = new AvesAblazeHardware();   // Use a Pushbot's hardware
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
		robot.init(hardwareMap);
		waitForStart();
		sleep(1);
		//Deploys the robot down from when it is at the starting position
		while(Math.abs(liftHeight-getLiftHeight())<3450&&!gamepad1.a){
			lift("up");
			telemetry.clearAll();
			telemetry.addData("liftHeight", getLiftHeight());
			telemetry.addData("startingHeight", liftHeight);
			telemetry.update();
		}
		lift("stop");

		//Once the robot reaches the floor it moves to the left and goes up. It then resets the linear slide
		moveLeftRight(0.75);
		sleep(250);
		moveUpDown(1);
		sleep(100);
		stopMotors();
		liftHeight=getLiftHeight();
		while(Math.abs(liftHeight-getLiftHeight())<3300&&!gamepad1.a){
			lift("down");
		}
		lift("stop");

		//Moves up to a point where it is able to rotate and find the Vuforia
		moveUpDown(1);
		sleep(200);
		stopMotors();
		moveLeftRight(-0.3);
		sleep(1500);
		rotate(-0.1);
		sleep(200);
		//If the robot is unable to find the Vuforia it continues to rotate
		while(!resetCoordinates()&&opModeIsActive()){
			rotate(0.1);
			sleep(200);
			stopMotors();
			sleep(200);
		}
		stopMotors();
		telemetry.clearAll();

		//Uses information from Vuforia and prints it and sets variables to use Vuforia information and calibrates the REV imu
		robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		telemetry.addData("angle", robot.rotation.thirdAngle);
		telemetry.update();
		robot.rotation=Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		robot.translation=robot.lastLocation.getTranslation();
		rotateToAngle(135);
		calibrate();
		robot.startingAngle=135;
		while(resetCoordinates()&&opModeIsActive()&&Math.abs(Math.abs(getY())+Math.abs(getX()))<59){
			moveUpDown(0.1);
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", getX());
			telemetry.addData("y", getY());
			telemetry.update();
			while(gamepad1.a){
				stopMotors();
			}
			while(gamepad1.a){
				stopMotors();
			}
			robot.translation=robot.lastLocation.getTranslation();
			telemetry.addData("x", robot.translation.get(0));
			telemetry.addData("y", robot.translation.get(1));
			telemetry.update();
			while(gamepad1.a){
				stopMotors();
			}
		}
		stopMotors();


		sleep(10000);
		Callable<Boolean> checkDistance=new Callable<Boolean>() {
			public Boolean call() {
				return !(robot.sensorDistance.getDistance(DistanceUnit.INCH)<20)&&opModeIsActive()&&resetCoordinates();
			}
		};

		//Reads color of the left material
		moveLeftRight(0.2, checkDistance);
		sleep(100);
		stopMotors();
		color=checkColor();
		rotateToAngle(135);
		sleep(200);
		//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.

		if(color.equals("yellow")){
			/*
			robot.drive(23,true,0.6);
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
			*/
		}

		//If the mineral is not gold it will read color of the center mineral
		else {
			moveLeftRight(0.2);
			sleep(700);
			moveLeftRight(0.2, checkDistance);
			telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
			telemetry.update();
			sleep(100);
			color=checkColor();
			rotateToAngle(135);

			//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.
			stopMotors();
			sleep(1000);
			sleep(200);
			if(color.equals("yellow")){
				/*
				robot.drive(29,true,0.6);
				robot.stopMotors();
				sleep(1500);
				robot.rotateToAngle(180);
				robot.drive(6, true,0.8);
				robot.marker1.setPosition(0.3);
				sleep(1000);
				robot.marker1.setPosition(1);
				*/
			}
			//If neither the left nor the center mineral is gold it will move to the right mineral
			else {
				moveLeftRight(0.2);
				sleep(1000);
				moveLeftRight(0.2, checkDistance);
				telemetry.addData("distance", robot.sensorDistance.getDistance(DistanceUnit.INCH));
				telemetry.update();
				stopMotors();
				//If the mineral is gold the robot will move forward towards the depot while pushing the gold mineral with it. It will then drop the marker.
				/*
				robot.drive(21, true,0.6);
				robot.stopMotors();
				sleep(1500);
				robot.rotateToAngle(180);
				robot.drive(23, true,0.8);
				robot.marker1.setPosition(0.3);
				sleep(1000);
				robot.marker1.setPosition(1);
				*/
			}


			stopMotors();


		}

		//While the code is running it will constantly print the color, angle, lift height, and coordinates
		// robot.rotateToAngle(265);
		// robot.drive(63, true,0.8);
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
