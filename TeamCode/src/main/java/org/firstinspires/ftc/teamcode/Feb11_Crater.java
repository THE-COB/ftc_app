package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * Created by NOAH PELBERG on 1/22/19.
 */

@Autonomous(name = "Feb11Crater", group = "AAA")
public class Feb11_Crater extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	public class initTfod implements Runnable{
		@Override
		public void run() {
			initVuforia();


			if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
				initTfod();
			} else {
				telemetry.addData("Sorry!", "This device is not compatible with TFOD");
			}
			/** Activate Tensor Flow Object Detection. */
			if (tfod != null) {
				tfod.activate();
			}
		}
	}
	@Override
	public void runOpMode() {

		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		robot.phoneServoX.setPosition(0.89);		//FEB 22
		robot.phoneServoY.setPosition(0.7);	//FEB 22
		waitForStart();
		runtime = new ElapsedTime();
if(opModeIsActive()){
	new Thread(new initTfod()).start();
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
	rotate(-0.01);
	sleep(500);
	stopMotors();

	stopMotors();

	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99 - 1));
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95 - 1));
	robot.startingAngle = 42;
	rotateToAngle(45);
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
	sample();
	telemetry.addData("Marker Color", position);
	telemetry.update();

	rotate(-1);
	sleep(220);
	rotateToAngle(90);
	moveLeftRight(0.8);
	sleep(100);
	robot.wallServo.setPosition(0.92);
	moveUpDown(1);
	ElapsedTime wallTime=new ElapsedTime();
	while(opModeIsActive()&&wallTime.seconds()<1.15);
	robot.wallServo.setPosition(0.3);
	rotate(1);
	sleep(500);
	rotateToAngle(270);
	moveLeftRight(-1);
	markerBlue=robot.markerColor.blue();
	markerRed=robot.markerColor.red();
	sleep(2250);
	ElapsedTime depotTime=new ElapsedTime();
	while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15&&opModeIsActive()&&depotTime.seconds()<1)
		moveLeftRight(-0.2);
	rotate(1);
	robot.marker1.setPosition(0.3);
	sleep(350);
	stopMotors();

	sleep(500);
	robot.marker1.setPosition(1);
	moveUpDown(-1);
	sleep(20);
	rotateToAngle(4);	//changed feb 21

	moveUpDown(1);
	ElapsedTime drivetime= new ElapsedTime();
	while(opModeIsActive()&&drivetime.seconds()<1.45&&runtime.seconds()<28.5){
	}
	if(drivetime.seconds()>1.6 || !opModeIsActive()) {
		stopMotors();
	}
	else{
		robot.extension.setPower(0.5);
	}
	robot.arm.setPower(1);
	ElapsedTime armTime=new ElapsedTime();
	while(armTime.seconds()<1.2&&opModeIsActive()){
		if(drivetime.seconds()>1.65){
			stopMotors();
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
