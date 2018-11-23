package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="PolarDriveTest", group="Test")

public class PolarDriveTest extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double position=0.8;
	double startingPosition;
	int goldMinerals;
	int silverMinerals;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		while(opModeIsActive()) {
			double c = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
           double angle=Math.atan(gamepad1.left_stick_y/gamepad1.left_stick_x)-(Math.PI/4);
           telemetry.addData("angle", angle/Math.PI+"pi");
           telemetry.update();
			robot.motor0.setPower(Math.cos(angle));
			robot.motor1.setPower(Math.sin(angle));
			robot.motor3.setPower(-Math.cos(angle));
			robot.motor2.setPower(-Math.sin(angle));
		}


	}

}
