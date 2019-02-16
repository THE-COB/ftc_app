package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "TeamMarker", group = "AAA")
@Disabled
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
if(opModeIsActive()) {
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
	sleep(300);
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
robot.startingAngle=135;
	moveLeftRight(-1);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5 - 1));
	sleep(280);
	polarDrive(1, 2.52);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
	sleep(2300);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(3 - 1));
	rotateToAngle(190);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
	moveLeftRight(-1);
	sleep(900);
	robot.marker1.setPosition(0.3);
	sleep(230);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93 - 1));
	moveLeftRight(1);
	sleep(200);
	robot.marker1.setPosition(1);
	rotateToAngle(270);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(94 - 1));
	moveUpDown(1);
	sleep(1550);
	stopMotors();


	stopMotors();
	robot.arm.setPower(1);
	sleep(1000);
	robot.arm.setPower(0);
	while (opModeIsActive()) {
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
		sleep(100);
	}
}
	}}
