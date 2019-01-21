package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="Depot", group="AAA")
//Rohan Don't touch this I swear to God
public class BlueDepotLights extends AvesAblazeOpmode {

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
		/*robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9-1));
		robot.arm.setPower(1);
		sleep(400);
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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));*/
		ElapsedTime scanTime = new ElapsedTime();
		stopMotors();
position="right";
while(opModeIsActive()&&(!robot.imu.isGyroCalibrated()||!robot.imu1.isGyroCalibrated())&&scanTime.seconds()<6){
	calibrate();
	robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(38-1));

}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
robot.startingAngle=135;
rotateToAngle(135);


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
			rotateToAngle(180);
			rotate(1);
			sleep(60);
			robot.marker1.setPosition(1);
			moveUpDown(-1);
			sleep(100);
			moveLeftRight(-1);
			sleep(520);
			moveUpDown(-1);
			sleep(2150);
			stopMotors();
		}
		else if(position.equals("right")){
			polarDrive(1, Math.PI/3-0.2);
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
			polarDrive(1, 2*Math.PI/2.6);
			sleep(1200);
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
			sleep(400);
			moveLeftRight(-1);
			sleep(250);
			moveUpDown(-1);
			sleep(2600);
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
