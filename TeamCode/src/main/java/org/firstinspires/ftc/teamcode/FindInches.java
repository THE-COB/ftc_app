package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Find inches", group = "pushbot")
@Disabled
public class FindInches extends LinearOpMode {

	AvesAblazeHardwarePushbot robot   = new AvesAblazeHardwarePushbot();

	@Override
	public void runOpMode() {

		robot.init(hardwareMap);
		telemetry.addData("status", "ready");
		telemetry.update();
		waitForStart();

		while(opModeIsActive()){
			/*//Test 1: in
			if(gamepad1.a) robot.moveUpDown(0.25,250);
			if(gamepad1.b) robot.moveUpDown(0.25,500);
			if(gamepad1.x) robot.moveUpDown(0.25,750);
			if(gamepad1.y) robot.moveUpDown(0.25,1000);

			if(gamepad1.dpad_up) robot.moveUpDown(0.5,250);
			if(gamepad1.dpad_down) robot.moveUpDown(0.5,500);
			if(gamepad1.dpad_left) robot.moveUpDown(0.5,750);
			if(gamepad1.dpad_right) robot.moveUpDown(0.5,1000);

			telemetry.addData("motor0",robot.motor0.getCurrentPosition());
			telemetry.addData("motor1",robot.motor1.getCurrentPosition());
			telemetry.addData("motor2",robot.motor2.getCurrentPosition());
			telemetry.addData("motor3",robot.motor3.getCurrentPosition());

			telemetry.update();*/

			if(gamepad1.a) robot.drive(3,true,0.8);
			if(gamepad1.b) robot.drive(6,true,0.8);
			if(gamepad1.x) robot.drive(9,true,0.8);
			if(gamepad1.y) robot.drive(12,true,0.8);
		}
	}
}
