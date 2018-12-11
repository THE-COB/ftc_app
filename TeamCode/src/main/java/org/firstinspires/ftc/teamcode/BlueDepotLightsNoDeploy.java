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
			telemetry.addData("position", position.equals("none"));
			telemetry.update();
		}

		robot.startingAngle=120;
		if(position.equals("none")){
			position.equals("right");
		}
		while(opModeIsActive()&&!gamepad2.a&&!gamepad1.a){
			telemetry.addData("position", position);
			telemetry.update();
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
			rotateToAngle(180);
			moveLeftRight(1);
			sleep(150);
			moveUpDown(-1);
			sleep(250);
			moveLeftRight(-1);
			sleep(300);
			moveUpDown(-1);
			sleep(2250);
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
