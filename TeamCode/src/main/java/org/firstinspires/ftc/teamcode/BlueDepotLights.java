package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="BlueDepotLights", group="Competition")
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
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2-1));
		initVuforia();

		tfod.activate();
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
		sleep(100);
		stopMotors();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19-1));
		lower();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50-1));
		ElapsedTime scanTime = new ElapsedTime();
		while(opModeIsActive()&&position.equals("nothing")&&scanTime.seconds()<3||(!robot.imu.isGyroCalibrated()&&!robot.imu1.isGyroCalibrated())) {
			check2Minerals();
			calibrate();

			telemetry.addData("Gold Mineral", position);
			telemetry.addData("position", position.equals("nothing"));
			telemetry.update();
		}
		robot.startingAngle=125;
		if(position.equals("nothing")){
			position.equals("right");
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(18-1));
		moveUpDown(1);
		sleep(100);
		rotateToAngle(135);
		//if(position.equals("center")){
		moveLeftRight(-1);
		moveUpDown(1);
		sleep(1100);
		rotate(-1);
		sleep(700);
		robot.marker1.setPosition(0.3);
		moveLeftRight(-1);
		sleep(200);
		moveLeftRight(1);
		sleep(100);
		rotateToAngle(0);
		robot.marker1.setPosition(1);
		moveUpDown(1);
		sleep(100);
		moveLeftRight(1);
		sleep(1000);
		moveUpDown(1);
		sleep(2150);
		//}
		stopMotors();
		while(runtime.seconds()<28.5);
		robot.arm.setPower(1);
		sleep(700);
		robot.arm.setPower(0);


	}

}
