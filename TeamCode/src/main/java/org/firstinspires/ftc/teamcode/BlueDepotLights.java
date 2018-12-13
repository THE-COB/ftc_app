package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="Depot", group="AAA")
//Rohan Don't touch this I swear to God
public class BlueDepotLights extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
		initVuforia();
		calibrate();
		tfod.activate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		waitForStart();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9-1));
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
		sleep(320);
		stopMotors();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19-1));
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));
		ElapsedTime scanTime = new ElapsedTime();
		rotate(-0.5);
		sleep(300);
		stopMotors();

		boolean seesMinerals=false;
		while (opModeIsActive() &&  scanTime.seconds() < 4 || (!robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated())) {
			calibrate();

		}
		rotate(0.04);
		while(position.equals("none")&&opModeIsActive()||!gamepad1.a){
			check2Minerals();
			try {
				if (tfod.getRecognitions() != null) {
					telemetry.addData("recognitions", tfod.getRecognitions());
					telemetry.addData("label 0", tfod.getRecognitions().get(0).getLabel());
					telemetry.addData("label 0", tfod.getRecognitions().get(1).getLabel());
					telemetry.addData("position", position);
					telemetry.update();
				}
			}
			catch (Exception e){

			}
			if(!position.equals("none")&&opModeIsActive())
				stopMotors();

		}
		robot.startingAngle = 135;
		stopMotors();
		rotateToAngle(135);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(18-1));

		if(position.equals("none")) {
			position="right";
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(48-1));
		}


		if(position.equals("center")){
			moveUpDown(1);
			sleep(100);
			rotateToAngle(135);
			moveLeftRight(-1);
			moveUpDown(1);
			sleep(1100);
			rotate(-1);
			sleep(700);
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			sleep(200);
			moveLeftRight(1);
			rotateToAngle(180);
			robot.marker1.setPosition(1);
			moveUpDown(-1);
			sleep(100);
			moveLeftRight(-1);
			sleep(650);
			moveUpDown(-1);
			sleep(2150);
			stopMotors();
			robot.extension.setPower(1);
			sleep(1200);
			robot.extension.setPower(0);
			while(runtime.seconds()<28&&opModeIsActive());
			robot.arm.setPower(1);
			sleep(900);
			robot.arm.setPower(0);
		}
		else if(position.equals("right")){
			polarDrive(1, Math.PI/3-0.2);
			sleep(1500);
			rotateToAngle(165);
			moveUpDown(1);
			sleep(1200);
			rotate(-1);
			sleep(700);
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			sleep(200);
			moveLeftRight(1);
			rotateToAngle(180);
			robot.marker1.setPosition(1);
			moveUpDown(-1);
			sleep(100);
			moveLeftRight(-1);
			sleep(450);
			moveUpDown(-1);
			sleep(2150);
			stopMotors();
			robot.extension.setPower(1);
			sleep(1200);
			robot.extension.setPower(0);
			while(runtime.seconds()<28&&opModeIsActive());
			robot.arm.setPower(1);
			sleep(900);
			robot.arm.setPower(0);
		}
		else{
			polarDrive(1, 2*Math.PI/2.7);
			sleep(820);
			rotateToAngle(180);
			moveUpDown(1);
			sleep(420);
			moveLeftRight(-1);
			sleep(350);
			robot.marker1.setPosition(0.3);
			sleep(830);
			robot.marker1.setPosition(1);
			rotateToAngle(176);
			moveLeftRight(1);
			sleep(150);
			moveUpDown(-1);
			sleep(250);
			moveLeftRight(-1);
			sleep(400);
			moveUpDown(-1);
			sleep(2150);
			stopMotors();
			robot.extension.setPower(1);
			sleep(1200);
			robot.extension.setPower(0);
			while(runtime.seconds()<28&&opModeIsActive());
			robot.arm.setPower(1);
			sleep(900);
			robot.arm.setPower(0);
		}

		stopMotors();
		tfod.deactivate();

	}

}
