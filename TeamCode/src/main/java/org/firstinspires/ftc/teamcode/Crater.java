package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="Crater", group="AAA")
//Rohan Don't touch this I swear to God
public class Crater extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	@Override
	public void runOpMode() {

/*		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		initVuforia();

		tfod.activate();
		waitForStart();
		*//*robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9-1));
		robot.arm.setPower(1);
		sleep(700);
		robot.arm.setPower(0);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39-1));
		lift();
		telemetry.addData("status", "deployed");

		telemetry.update();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93-1));
		rotate(0.5);
		sleep(200);
		moveLeftRight(1);
		sleep(100);
		rotate(-0.5);
		sleep(150);
		stopMotors();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19-1));
		lower();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));
		ElapsedTime scanTime = new ElapsedTime();
		while (opModeIsActive() &&  scanTime.seconds() < 4 && !robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated()) {
			calibrate();
			check2Minerals();
		}

		sleep(10000);
		robot.startingAngle = 110;
		if(position.equals("none")) {
			position="right";
			while(opModeIsActive());
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(18-1));
*//*
		ElapsedTime scanTime = new ElapsedTime();
		while (opModeIsActive() &&  scanTime.seconds() < 4 && !robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated()) {
			calibrate();
			check2Minerals();
		}*/
		robot.startingAngle=29;
		if(position.equals("center")||gamepad1.b){
			polarDrive(1, 2*Math.PI/2.97);
			sleep(1420);
			polarDrive(1, -(Math.PI-(2*Math.PI/2.9)));
			sleep(1270);

		}
		else if(position.equals("right")||gamepad1.a){
			polarDrive(1, Math.PI/3-0.1);
			sleep(2000);
			polarDrive(1, -(Math.PI-(Math.PI/3-0.1)));
			sleep(1500);

		}
		else{
			polarDrive(1, 2*Math.PI/2.7);
			sleep(1420);
			polarDrive(1, -(Math.PI-(2*Math.PI/2.7))+0.15);
			sleep(1320);
		}
		polarDrive(1,2*Math.PI/2.25);
		sleep(1700);
		rotateToAngle(270);
		moveLeftRight(-1);
		sleep(1400);
		robot.marker1.setPosition(0.3);
		sleep(830);
		robot.marker1.setPosition(1);
		rotateToAngle(170);
		moveUpDown(-1);
		sleep(2800);
		stopMotors();
		robot.extension.setPower(1);
		sleep(1200);
		robot.extension.setPower(0);
		tfod.deactivate();

	}

}
