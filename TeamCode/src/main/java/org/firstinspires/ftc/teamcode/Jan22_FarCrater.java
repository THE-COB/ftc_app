package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@TeleOp(name = "FarCrater", group = "AAA")
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
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		waitForStart();

		initVuforia();


		if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
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
		finalMinFinder();
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


		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99-1));
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
			sleep(400);
			rotateToAngle(135);
			moveUpDown(1);
			sleep(1400);
			rotate(-1);
			sleep(700);
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			sleep(200);
			moveLeftRight(1);
			rotateToAngle(270);
			rotate(1);
			sleep(60);
			robot.marker1.setPosition(1);
			rotateToAngle(270);
			moveLeftRight(-1);
			sleep(200);
			moveUpDown(1);
			sleep(2500);
			stopMotors();
		}
		else if(position.equals("right")){
			polarDrive(1, Math.PI/3-0.35);
			sleep(1650);
			rotateToAngle(165);
			moveUpDown(1);
			sleep(1200);
			rotate(-1);
			sleep(700);
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			sleep(200);
			moveLeftRight(1);
			rotateToAngle(270);
			robot.marker1.setPosition(1);
			rotateToAngle(270);
			moveLeftRight(-1);
			sleep(200);
			moveUpDown(1);
			sleep(2500);
			stopMotors();
		}
		else{

			moveLeftRight(-1);
			sleep(550);
			polarDrive(1, 2*Math.PI/2.5);
			sleep(1400);
			rotateToAngle(180);
			moveUpDown(1);
			sleep(620);
			moveLeftRight(-1);
			sleep(550);
			robot.marker1.setPosition(0.3);
			sleep(830);
			robot.marker1.setPosition(1);
			rotateToAngle(270);
			moveLeftRight(1);
			sleep(200);
			moveUpDown(1);
			sleep(2100);
			moveLeftRight(-1);
			sleep(200);
			moveUpDown(1);
			sleep(400);
			stopMotors();
		}

		stopMotors();
		while(opModeIsActive()){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			sleep(100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
			sleep(100);
		}
	}
}
