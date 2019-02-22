package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "Feb6DepotFarCrater", group = "AAA")
public class Feb6_FarCrater extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;

	@Override
	public void runOpMode() {

		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		waitForStart();
		runtime = new ElapsedTime();
if(opModeIsActive()){
		initVuforia();


		if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
			initTfod();
		} else {
			telemetry.addData("Sorry!", "This device is not compatible with TFOD");
		}

	ElapsedTime scanTime = new ElapsedTime();
	while(position.equals("none")&&opModeIsActive()) {
		finalMinFinder();
		if (Math.round(scanTime.milliseconds() / 200) % 16 == 0) {
			robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() - 0.00195);
			robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() + 0.00105);
		}
	}

	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9 - 1));
	robot.arm.setPower(1);
	sleep(600);
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

	stopMotors();

	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99 - 1));
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95 - 1));
	robot.startingAngle = 135;
	rotateToAngle(135);
	if (position.equals("left") || gamepad1.b) {
		polarDrive(1, 2 * Math.PI / 2.97);
		sleep(1420);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
		polarDrive(1, -(Math.PI - (2 * Math.PI / 2.9)));
		sleep(1320);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

	} else if (position.equals("right") || gamepad1.a) {
		polarDrive(1, Math.PI / 4.4);
		sleep(2220);
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
	rotateToAngle(180);
	moveLeftRight(1);
	sleep(100);
	robot.wallServo.setPosition(0.82);
	moveUpDown(1);
	sleep(700);
	robot.wallServo.setPosition(0.3);
	moveLeftRight(-1);
	markerBlue=robot.markerColor.blue();
	markerRed=robot.markerColor.red();
	sleep(900);
	while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15)
		moveLeftRight(-0.2);
	rotate(-1);
	sleep(350);
	stopMotors();
	robot.marker1.setPosition(0.3);
	sleep(500);
	robot.marker1.setPosition(1);
	rotateToAngle(270);

	moveUpDown(1);
	ElapsedTime drivetime= new ElapsedTime();
	while(opModeIsActive()&&drivetime.seconds()<1.8&&runtime.seconds()<28.5){
	}
	if(drivetime.seconds()>1.8) {
		stopMotors();
	}
	else{
		robot.extension.setPower(0.5);
	}
	robot.arm.setPower(1);
	ElapsedTime armTime=new ElapsedTime();
	while(armTime.seconds()<1.3){
		if(drivetime.seconds()>1.8){
			stopMotors();
			robot.extension.setPower(0);
		}
	}
	robot.arm.setPower(0);
	robot.extension.setPower(0);
	stopMotors();


	/*moveUpDown(1);
	sleep(1850);
	stopMotors();
	robot.arm.setPower(1);
	sleep(1500);
	robot.arm.setPower(0);
	robot.extension.setPower(0.5);
	sleep(250);
	robot.extension.setPower(0);*/
	while (opModeIsActive()) {
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
		sleep(100);
	}
	}}
}
