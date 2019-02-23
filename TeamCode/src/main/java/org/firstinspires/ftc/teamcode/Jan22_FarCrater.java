package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "DepotFarCrater", group = "AAA")
@Disabled
public class Jan22_FarCrater extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;

	@Override
	public void runOpMode() {
		runtime = new ElapsedTime();
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		//calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		waitForStart();
if(opModeIsActive()){
		initVuforia();


		/*if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
			initTfod();
		} else {
			telemetry.addData("Sorry!", "This device is not compatible with TFOD");
		}

		while(opModeIsActive()&&position.equals("none")){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29-1));
			finalMinFinder();
		}
		while(opModeIsActive()&&position.equals("none")){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29-1));
			finalMinFinder();
		}
		while(opModeIsActive()&&position.equals("none")){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29-1));
			finalMinFinder();
		}
		while(opModeIsActive()&&position.equals("none")){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29-1));
			finalMinFinder();
		}
		finalMinFinder();*/
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9-1));
		robot.arm.setPower(1);
		sleep(500);
		robot.arm.setPower(0);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39-1));

		lift();
		telemetry.addData("status", "deployed");
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));

		telemetry.update();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		sleep(150);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
		sleep(150);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		sleep(150);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93-1));
		rotate(0.5);
		sleep(200);
		moveLeftRight(1);
		sleep(370);
		stopMotors();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19-1));
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93-1));
		rotate(-0.1);
		sleep(500);
		stopMotors();

		ElapsedTime scanTime = new ElapsedTime();
		stopMotors();


		/*robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99-1));
		while(opModeIsActive()&&(!robot.imu.isGyroCalibrated()&&!robot.imu1.isGyroCalibrated())){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(97-1));
			calibrate();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
			finalMinFinder();
			telemetry.addData("scantime", scanTime.seconds());
			telemetry.update();
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95-1));
		robot.startingAngle=135;
		rotateToAngle(135);


		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91-1));
		if(position.equals("center")){
			moveLeftRight(-1);
			sleep(250);
			polarDrive(1,Math.PI/2-0.15);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			sleep(400);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
			rotateToAngle(135);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3-1));
			moveUpDown(1);
			sleep(1400);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			rotate(-1);
			sleep(700);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(1-1));
			robot.marker1.setPosition(0.3);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(87-1));
			moveLeftRight(-1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(79-1));
			sleep(200);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(30-1));
			moveLeftRight(1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(31-1));
			rotateToAngle(270);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(29-1));
			rotate(1);
			sleep(60);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			robot.marker1.setPosition(1);
			rotateToAngle(270);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
			moveLeftRight(-1);
			sleep(200);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3-1));
			moveUpDown(1);
			sleep(2500);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			stopMotors();
		}
		else if(position.equals("right")){
			polarDrive(1, Math.PI/3-0.35);
			sleep(1650);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			rotateToAngle(165);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
			moveUpDown(1);
			sleep(1200);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91-1));
			rotate(-1);
			sleep(700);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3-1));
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			sleep(200);
			moveLeftRight(1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(1-1));
			rotateToAngle(270);
			robot.marker1.setPosition(1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(87-1));
			rotateToAngle(270);
			moveLeftRight(-1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(84-1));
			sleep(500);
			moveUpDown(1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(1-1));
			sleep(2500);
			stopMotors();
		}
		else{

			moveLeftRight(-1);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			sleep(350);
			polarDrive(1, 2*Math.PI/2.3);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
			sleep(1400);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3-1));
			rotateToAngle(180);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			moveUpDown(1);
			sleep(420);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91-1));
			moveLeftRight(-1);
			sleep(550);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(89-1));
			robot.marker1.setPosition(0.3);
			sleep(830);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93-1));
			robot.marker1.setPosition(1);
			rotateToAngle(270);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(94-1));
			moveLeftRight(1);
			sleep(200);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(97-1));
			moveUpDown(1);
			sleep(2100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			moveLeftRight(-1);
			sleep(350);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			moveUpDown(1);
			sleep(200);
			stopMotors();
		}

		stopMotors();
	robot.arm.setPower(1);
	sleep(1000);
	robot.arm.setPower(0);*/
		while(opModeIsActive()){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			sleep(100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
			sleep(100);
		}
	}}
}
