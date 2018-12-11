package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="BlueDepotLightsNoDeploy", group="Competition")
//Rohan Don't touch this I swear to God
public class BlueDepotLightsNoDeploy extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));

		robot.tfod.activate();
		waitForStart();
		ElapsedTime scanTime = new ElapsedTime();
		while(opModeIsActive()&&position.equals("nothing")&&scanTime.seconds()<3||(!robot.imu.isGyroCalibrated()&&!robot.imu1.isGyroCalibrated())) {
			check2Minerals();
			calibrate();

			telemetry.addData("Gold Mineral", position);
			telemetry.addData("position", position.equals("nothing"));
			telemetry.update();
		}
		robot.startingAngle=122;
		if(position.equals("nothing")){
			position.equals("right");
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(18-1));

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
		else{
			moveUpDown(1);
			sleep(100);
			moveLeftRight(-1);
			sleep(500);
			rotateToAngle(155);
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
		stopMotors();


	}

}
