package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "DepotFarCrater", group = "AAA")
public class Jan31_TeamMarker extends  AvesAblazeOpmode {

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
if(opModeIsActive()){

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
	robot.arm.setPower(0);
		while(opModeIsActive()){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			sleep(100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
			sleep(100);
		}
	}}
}
