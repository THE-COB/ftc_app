package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="Prom", group="AAA")

//Rohan Don't touch this I swear to God
public class Prom extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	@Override
	public void runOpMode() {
		runtime = new ElapsedTime();
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(96 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(79 - 1));
		waitForStart();
		robot.lid.setPosition(0.8);
		//P
		moveUpDown(1);
		sleep(3000);
		rotateToAngle(0);
		moveLeftRight(1);
		sleep(1500);
		rotateToAngle(0);
		moveUpDown(-1);
		sleep(1500);
		rotateToAngle(0);
		moveLeftRight(-1);
		sleep(1500);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveLeftRight(1);
		sleep(1000);
		rotateToAngle(0);
		moveUpDown(-1);
		sleep(1500);
		rotateToAngle(0);
		robot.lid.setPosition(0.8);
		//R
		moveUpDown(1);
		sleep(3000);
		rotateToAngle(0);
		moveLeftRight(1);
		sleep(1500);
		rotateToAngle(0);
		moveUpDown(-1);
		sleep(1500);
		rotateToAngle(0);
		moveLeftRight(-1);
		sleep(1500);
		rotateToAngle(0);
		polarDrive(1,-Math.PI/3.7);
		sleep(3300);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveLeftRight(1);
		sleep(2500);
		rotateToAngle(0);
		robot.lid.setPosition(0.8);
		//O
		moveUpDown(1);
		sleep(3000);
		rotateToAngle(0);
		moveLeftRight(1);
		sleep(1500);
		rotateToAngle(0);
		moveUpDown(-1);
		sleep(3000);
		rotateToAngle(0);
		moveLeftRight(-1);
		sleep(1500);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveLeftRight(1);
		sleep(2500);
		rotateToAngle(0);
		robot.lid.setPosition(0.8);
		//M
		moveUpDown(1);
		sleep(3000);
		rotateToAngle(0);
		polarDrive(1,-Math.PI/4);
		sleep(3000);
		rotateToAngle(0);
		polarDrive(1,Math.PI/4);
		sleep(3000);
		rotateToAngle(0);
		moveUpDown(-1);
		sleep(3000);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveLeftRight(1);
		sleep(1000);
		rotateToAngle(0);
		robot.lid.setPosition(0.8);
		//?
		moveUpDown(1);
		sleep(600);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveUpDown(1);
		sleep(500);
		rotateToAngle(0);
		robot.lid.setPosition(0.8);
		moveUpDown(1);
		sleep(500);
		rotateToAngle(0);
		polarDrive(1,Math.PI/4);
		sleep(2000);
		rotateToAngle(0);
		polarDrive(1,3*Math.PI/4);
		sleep(2000);
		rotateToAngle(0);
		polarDrive(1,-3*Math.PI/4);
		sleep(2000);
		rotateToAngle(0);
		robot.lid.setPosition(0.3);
		moveLeftRight(2000);
		rotateToAngle(0);
		stopMotors();

	}

}
