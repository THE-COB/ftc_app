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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39-1));
		//lift();
		telemetry.addData("status", "deployed");
		/*lift("up");
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
		lift("stop");

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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));*/
		ElapsedTime scanTime = new ElapsedTime();
		sleep(300);
		stopMotors();

		boolean seesMinerals=false;
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
			sleep(2350);
			stopMotors();

		}

		stopMotors();
		tfod.deactivate();

	}

}
