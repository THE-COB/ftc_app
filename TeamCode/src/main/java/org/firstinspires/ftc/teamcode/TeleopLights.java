package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="Teleop Lights", group="Competition")
@Disabled
//Rohan Don't touch this I swear to God
public class TeleopLights extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	double startingPosition=0;
	int position;
	boolean allDirDrive = false;
	int relativeDrive = 10000;
	@Override
	public void runOpMode() {
		try {
			runtime = new ElapsedTime();
			robot.init(hardwareMap);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50));
		} catch (Exception e) {
//			tfod.shutdown();
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(60));
		while (opModeIsActive() && (!robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated())) {
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(97 - 1));
			calibrate();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
			finalMinFinder();


		}
		waitForStart();
		if (opModeIsActive()) {
			runtime.reset();
			position = 57;
			while (opModeIsActive()) {

				telemetry.addData("time", Math.round(runtime.seconds()));
				telemetry.addData("armLength", robot.extension.getCurrentPosition());
				telemetry.addData("liftHeight", robot.lift1.getCurrentPosition());
				telemetry.addData("armPosition", robot.arm.getCurrentPosition());
				telemetry.addData("angle", getAngle());
				telemetry.update();
				if (runtime.seconds() > 120) {
				} else if (runtime.seconds() > 115) {
				} else if (runtime.seconds() > 105) {
				}
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(position - 1));
				//Move Robot
				moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
				moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
				rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*
			PUT FINAL CONTROL SCHEME HERE


			 */
				if ((Math.abs(moveY) > 0.25)) {
					moveUpDown(-moveY);
					position = 57;

				} else if (Math.abs(moveX) > 0.25) {
					moveLeftRight(-moveX);
					position = 67;
				} else if (Math.abs(rotate) > 0.25) {
					rotate(-rotate);
				}
			/*if(!allDirDrive && relativeDrive == 10000) {
				if ((Math.abs(moveY) > 0.25)) {
					moveUpDown(-moveY);
					position = 57;

				} else if (Math.abs(moveX) > 0.25) {
					moveLeftRight(-moveX);
					position = 67;
				}
			}
			else if(allDirDrive){
				if(Math.abs(moveX)>0.25 || Math.abs(moveY)>0.25) {
					if(moveX == 0){
						moveUpDown(moveY);
					}
					else if(moveY == 0){
						moveLeftRight(moveX);
					}
					else {
						polarDrive(Math.abs(Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2))), Math.atan(moveY / moveX));
					}
				}
			}
			else if(relativeDrive != 10000){
				int angleFacing = getAngle();
				int angleWanted = 0;
				double powerControl = 0;
				if(Math.abs(moveX)>0.25){
					powerControl = Math.abs(moveX);
					if(moveX>0){
						angleWanted = 0;
					}
					else{
						angleWanted = 180;
					}
				}
				else if(Math.abs(moveY)>0.25){
					powerControl = Math.abs(moveY);
					if(moveY>0){
						angleWanted = 270;
					}
					else{
						angleWanted = 90;
					}
				}
				polarDrive(powerControl, Math.toRadians((360+angleWanted)-angleFacing));
			}*/
				else if (gamepad1.left_bumper) {
					rotate(-0.2);
					position = 39;
				} else if (gamepad1.right_bumper) {
					rotate(0.2);
					position = 39;
				} else if (gamepad1.dpad_up) {
					position = 64;
					moveUpDown(-0.3);
				} else if (gamepad1.dpad_down) {
					position = 64;
					moveUpDown(+0.3);
				} else if (gamepad1.dpad_left) {
					position = 54;
					moveLeftRight(-0.3);
				} else if (gamepad1.dpad_right) {
					position = 54;
					moveLeftRight(0.3);
				} else {
					position = 72;
					stopMotors();
				}

				//Move team marker mechanism
				if (gamepad1.left_trigger > 0.1) {
					robot.marker1.setPosition(1);
				}
				if (gamepad1.right_trigger > 0.1) {
					robot.marker1.setPosition(0.3);
				} else {
					//robot.marker.setPower(0.6);
				}

				//lift robot
				if (gamepad2.left_bumper) {
					position = 97;

				} else if (gamepad2.right_bumper) {
					if (runtime.seconds() < 110) {
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					}
				}

				if (gamepad2.dpad_up) {
					position = 1;
				} else if (gamepad2.dpad_down) {
					position = 2;
				} else if (gamepad1.y) {
					position = 3;
				} else if (gamepad1.a && !gamepad1.start) {
					position = 4;
				} else {
					lift("stop");
				}


				if (gamepad2.x) {
					//extends
					extensionPosition = startingPosition;
					//	while (opModeIsActive() && gamepad1.x) ;
				} else if (gamepad2.b && !gamepad2.start) {
					//retracts
					//	while (opModeIsActive() && gamepad1.b) ;
				} else if (gamepad2.y) {
				} else if (gamepad2.a && !gamepad2.start) {

				} else {
					robot.extension.setPower(0);
				}

				if (Math.abs(gamepad2.left_stick_y) > 0.1) {
					position = 22;
				} else if (gamepad2.left_trigger > 0.1) {
					position = 22;
				} else if (gamepad2.right_trigger > 0.1) {
					position = 22;
				} else
					robot.arm.setPower(0);

				//Controller 1 presses back, start, and both bumpers
			/*if(gamepad1.back && gamepad1.start && gamepad1.left_bumper && gamepad1.right_bumper){
				if(!allDirDrive) {
					allDirDrive = true;
				}
				else allDirDrive = false;
			}

			if(gamepad1.back && gamepad1.start && gamepad1.left_trigger>0.5 && gamepad1.right_trigger>0.5){
				if(relativeDrive == 10000) {
					if (gamepad1.a) {
						relativeDrive = 270;
					} else if (gamepad1.b) {
						relativeDrive = 0;
					} else if (gamepad1.x) {
						relativeDrive = 180;
					} else if (gamepad1.y) {
						relativeDrive = 90;
					}
					robot.startingAngle = relativeDrive;
					calibrate();
				}
				else{
					relativeDrive = 10000;
				}
			}*/
			}


		}
	}

}
