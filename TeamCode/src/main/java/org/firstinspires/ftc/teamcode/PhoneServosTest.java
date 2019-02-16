package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Rohan Mathur on 1/31/19.
 */

@TeleOp(name = "Phone Servo Test", group = "tests")
@Disabled
public class PhoneServosTest extends AvesAblazeOpmode {
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();

		while(opModeIsActive()) {
			if (gamepad1.dpad_left) {
				robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() - 0.02);
				while (gamepad1.dpad_left);
			}
			if (gamepad1.dpad_right) {
				robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() + 0.02);
				while (gamepad1.dpad_right);
			}

			if (gamepad1.dpad_up) {
				robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() + 0.02);
				while (gamepad1.dpad_up);
			}
			if (gamepad1.dpad_down) {
				robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() - 0.02);
				while (gamepad1.dpad_down);
			}
			telemetry.addData("servoX", robot.phoneServoX.getPosition());
			telemetry.addData("servoY", robot.phoneServoY.getPosition());
			telemetry.update();
		}
	}
}
