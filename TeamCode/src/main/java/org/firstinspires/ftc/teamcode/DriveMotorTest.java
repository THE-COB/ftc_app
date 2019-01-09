package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="DriveMotorTest", group="Test")
@Disabled
public class DriveMotorTest extends AvesAblazeOpmode {

	/* Declare OpMode members. */

	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		while (opModeIsActive()) {
			robot.motor0.setPower(gamepad1.left_stick_y);
			robot.motor1.setPower(gamepad1.right_stick_y);
			robot.motor2.setPower(gamepad1.left_trigger);
			robot.motor3.setPower(gamepad1.left_trigger);
		}

	}
}
