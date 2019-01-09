package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="DepotFarCrater", group="AAA")
//Rohan Don't touch this I swear to God
public class DepotFarCrater extends AvesAblazeOpmode {

	/* Declare OpMode members. */
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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9-1));
		robot.arm.setPower(1);
		sleep(400);
		robot.arm.setPower(0);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39-1));

		lift();
		telemetry.addData("status", "deployed");
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
	/*	lift("up");
		sleep(100);
		lift("stop");
		sleep(1000);
		robot.lift1.setPower(0.5);
		robot.lift2.setPower(0.5);
		sleep(150);
		while(Math.abs(robot.lift1.getCurrentPosition()-robot.startingHeight)<4000&&opModeIsActive()) {
			robot.lift1.setPower(0.5);
			robot.lift2.setPower(0.5);
			telemetry.addData("starting height", robot.startingHeight);
			telemetry.addData("height", getLiftHeight());
			telemetry.update();
		}
		robot.lift1.setPower(0.5);
		robot.lift2.setPower(0.5);
		sleep(300);
		lift("stop");*/

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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));
		ElapsedTime scanTime = new ElapsedTime();
		stopMotors();


		/*boolean seesMinerals=false;
		rotate(-0.5);
		sleep(500);
		rotate(0.03);
		while(position.equals("none")&&opModeIsActive()){
			check2Minerals();
			if(!position.equals("none")&&opModeIsActive())
				stopMotors();

		}
		stopMotors();
		boolean indeterminable;
		if(tfod.getRecognitions().get(0).getLabel().equals(goldMineralLabel)){
			indeterminable=tfod.getRecognitions().get(0).getLeft()<533&&tfod.getRecognitions().get(0).getLeft()>526;
		}
		else {
			indeterminable=tfod.getRecognitions().get(1).getLeft()<533&&tfod.getRecognitions().get(1).getLeft()>526;
		}
		if(indeterminable){
			rotate(0.1);
			sleep(100);
			check2Minerals();
		}
		robot.startingAngle = 135;
		stopMotors();
		rotateToAngle(135);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(18-1));

		if(position.equals("none")) {
			position="right";
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(48-1));
		}*/
position="center";
while(opModeIsActive()&&(!robot.imu.isGyroCalibrated()||!robot.imu1.isGyroCalibrated())&&scanTime.seconds()<6){
	calibrate();
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(38-1));

}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
robot.startingAngle=135;
rotateToAngle(135);


		if(position.equals("center")){
			moveLeftRight(-1);
			sleep(200);
			polarDrive(1,Math.PI/2-0.15);
			sleep(400);
			rotateToAngle(135);
			moveUpDown(1);
			sleep(1400);
			rotate(-1);
			sleep(700);
			robot.marker1.setPosition(0.3);
			moveLeftRight(-1);
			sleep(150);
			moveLeftRight(1);
			rotateToAngle(90);
			rotate(-1);
			sleep(50);
			robot.marker1.setPosition(1);
			moveUpDown(-1);
			sleep(100);
			moveLeftRight(1);
			sleep(220);
			moveUpDown(-1);
			sleep(2100);
			stopMotors();
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
		}
		else{

			moveLeftRight(-1);
			sleep(550);
			polarDrive(1, 2*Math.PI/2.7);
			sleep(920);
			rotateToAngle(180);
			moveUpDown(1);
			sleep(620);
			moveLeftRight(-1);
			sleep(550);
			robot.marker1.setPosition(0.3);
			sleep(830);
			robot.marker1.setPosition(1);
			rotateToAngle(176);
			moveLeftRight(1);
			sleep(250);
			moveUpDown(-1);
			sleep(250);
			moveLeftRight(-1);
			sleep(700);
			moveUpDown(-1);
			sleep(2300);
			stopMotors();

		}

		stopMotors();
		tfod.deactivate();
		while(opModeIsActive()){
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
			sleep(100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100-1));
			sleep(100);
		}

	}

}
