package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

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
		runtime = new ElapsedTime();
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		waitForStart();
		if (opModeIsActive()) {
			initVuforia();


			if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
				initTfod();
			} else {
				telemetry.addData("Sorry!", "This device is not compatible with TFOD");
			}

			while (opModeIsActive() && position.equals("none")) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29 - 1));
				finalMinFinder();
			}
			while (opModeIsActive() && position.equals("none")) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29 - 1));
				finalMinFinder();
			}
			while (opModeIsActive() && position.equals("none")) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29 - 1));
				finalMinFinder();
			}
			while (opModeIsActive() && position.equals("none")) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29 - 1));
				finalMinFinder();
			}
			while (opModeIsActive() && position.equals("none")) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29 - 1));
				finalMinFinder();
			}
			finalMinFinder();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9 - 1));
			robot.arm.setPower(1);
			sleep(500);
			robot.arm.setPower(0);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39 - 1));

			lift();
			telemetry.addData("status", "deployed");
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));

			telemetry.update();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
			sleep(150);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
			sleep(150);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
			sleep(150);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93 - 1));
			rotate(0.5);
			sleep(200);
			moveLeftRight(1);
			sleep(370);
			stopMotors();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19 - 1));
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93 - 1));
			rotate(-0.1);
			sleep(500);
			stopMotors();

			ElapsedTime scanTime = new ElapsedTime();
			stopMotors();


			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99 - 1));
			while (opModeIsActive() && (!robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated())) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(97 - 1));
				calibrate();
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
				finalMinFinder();
				telemetry.addData("scantime", scanTime.seconds());
				telemetry.update();
			}
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95 - 1));
			robot.startingAngle = 135;
			rotateToAngle(135);


			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91 - 1));

			if (position.equals("left") || gamepad1.b) {
				polarDrive(1, 2 * Math.PI / 2.97);
				sleep(1420);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
				polarDrive(1, -(Math.PI - (2 * Math.PI / 2.9)));
				sleep(1120);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

			} else if (position.equals("right") || gamepad1.a) {
				polarDrive(1, Math.PI / 4.4);
				sleep(2020);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
				polarDrive(1, -(Math.PI - (Math.PI / 4.4)));
				sleep(1450);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

			} else {
				polarDrive(1, Math.PI / 2.7);
				sleep(1120);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
				polarDrive(1, -(Math.PI - (Math.PI / 2.7)));
				sleep(800);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
			}
			polarDrive(1, 2 * Math.PI / 2.32);
			sleep(1900);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3-1));
			robot.startingAngle = 45;
			rotateToAngle(275);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			moveLeftRight(-1);
			sleep(1400);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(1-1));
			robot.marker1.setPosition(0.3);
			sleep(830);robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91-1));
			robot.marker1.setPosition(1);
			moveLeftRight(1);
			sleep(400);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			rotateToAngle(185);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(94-1));
			moveUpDown(-1);
			sleep(2250);
			stopMotors();
			while(opModeIsActive()){
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
				sleep(100);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
				sleep(100);
			}
			//	tfod.deactivate();

		}
	}

}
