package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Rohan Mathur on 10/24/18.
 */

@TeleOp(name="MRSuperSlow", group="pushbot")
public class MRSuperSlow extends AvesAblazeOpmode {
	DcMotor motor0;
	CRServo servo0;
	@Override
	public void runOpMode() {

		motor0 = hardwareMap.dcMotor.get("motor0");
		servo0 = hardwareMap.crservo.get("servo0");
		motor0.setDirection(DcMotorSimple.Direction.FORWARD);
		motor0.setPower(0);
		init();
		waitForStart();
		int position = 0;

		while (opModeIsActive()) {
			telemetry.addData("position", position);
			telemetry.update();
			if (gamepad1.a) {
				motor0.setPower(0.5);
			} else if (gamepad1.b) {
				motor0.setPower(-0.5);
			} else {
				motor0.setPower(0);
			}
			servo0.setPower(position);
			if (gamepad1.dpad_up) {
				position += 0.05;
				servo0.setPower(position);
				while (opModeIsActive() && gamepad1.dpad_up) ;
			}
			if (gamepad1.dpad_down) {
				position -= 0.05;
				servo0.setPower(position);
				while (opModeIsActive() && gamepad1.dpad_down) ;
			}

		}
	}
}

