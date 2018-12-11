package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="BlueDepot", group="Competition")

public class BlueDepot extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	// Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	public float moveY;
	public float moveX;
	public float rotate;
	public int liftHeight = 0;
	public double sd;
	public String color;
	public Orientation angles;
	public int mineralY;
	public int mineralX;
	public int mineralA;


	@Override
	public void runOpMode() {
		telemetry.addData("status", "calibrating");
		telemetry.update();
		robot.init(hardwareMap);
		telemetry.clearAll();
		robot.startingAngle=135;
		calibrate();
		while(robot.imu1.isGyroCalibrated()&&opModeIsActive()){

		}
		telemetry.addData("status", "ready");
		telemetry.update();
		waitForStart();
		robot.tfod.activate();
		//Deploys the robot down from when it is at the starting position
		robot.arm.setPower(1);
		sleep(400);
		robot.arm.setPower(-1);
		robot.arm.setPower(0);
		deploy();
		moveUpDown(1);
		sleep(200);
		polarDrive(0.5,2.8);
		lower();
		sleep(200);

		while (!resetCoordinates()&&opModeIsActive()&&Math.abs(getAngle()-robot.startingAngle)<15){
			rotate(-0.06);
		}
		while (!resetCoordinates()&&opModeIsActive()&&Math.abs(getAngle()-robot.startingAngle)<25){
			rotate(0.06);
		}
		while (!resetCoordinates()&&opModeIsActive()&&Math.abs(getAngle()-robot.startingAngle)<40){
			rotate(-0.2);
		}
		stopMotors();
		if(!resetCoordinates())
			position="right";
		else {
			stopMotors();
			calibrate();
			while (!robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated() && opModeIsActive())
				;
			robot.startingAngle = getAngle();
			/*try {
				moveToCoord(-62, -5, robot.startingAngle - 93, 0.25);
			} catch (IOException e) {
				while (!resetCoordinates() && opModeIsActive()) {
					rotate(0.1);
				}
				try {
					moveToCoord(-62, -5, robot.startingAngle - 93, 0.25);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}*/
			rotateToAngle(robot.startingAngle-93);
			telemetry.clearAll();
			telemetry.addData("status", "checking for sample field");
			checkMinerals();
			while (position.equals("none") && opModeIsActive()) {
				checkMinerals();
				rotate(0.04);
			}
		}
		rotateToAngle(180);
		if(position.equals("left")){
			moveUpDown(-1);
			sleep(120);
			moveLeftRight(1);
			sleep(960);
			moveLeftRight(-1);
			sleep(200);
			moveUpDown(1);
			sleep(200);
			moveLeftRight(1);
			sleep(550);
			robot.marker1.setPosition(0.3);
			sleep(830);
			robot.marker1.setPosition(1);
			rotateToAngle(180);
			moveLeftRight(-1);
			sleep(250);
			moveUpDown(-1);
			sleep(450);
			moveLeftRight(1);
			sleep(300);
			moveUpDown(-1);
			sleep(2450);
		}


		else if(position.equals("center")){
			moveUpDown(1);
			sleep(220);
			moveLeftRight(1);
			sleep(1650);
			moveUpDown(-1);
			sleep(600);
			stopMotors();
			sleep(50);
			moveUpDown(1);
			sleep(350);
			moveLeftRight(1);
			robot.marker1.setPosition(0.3);
			sleep(800);
			robot.marker1.setPosition(1);
			rotateToAngle(180);
			moveLeftRight(-1);
			sleep(250);
			moveUpDown(-1);
			sleep(450);
			moveLeftRight(1);
			sleep(200);
			moveUpDown(-1);
			sleep(2250);

		}
		//position="right"
		else{
			moveUpDown(1);
			sleep(220);
			moveLeftRight(1);
			sleep(900);
			robot.marker1.setPosition(0.3);
			sleep(1350);
			robot.marker1.setPosition(1);
			moveLeftRight(-1);
			sleep(370);
			moveUpDown(-1);
			sleep(900);
			moveLeftRight(1);
			sleep(700);
			moveUpDown(-1);
			sleep(1500);

		}
		stopMotors();
		stopMotors();
		while (opModeIsActive()){
			telemetry.addData("position", position);
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
