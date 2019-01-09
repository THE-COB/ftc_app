package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.PWMOutputImpl;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan Mathur on 9/24/18.
 */
@TeleOp(name="PreferredLights", group="Pushbot")
@Disabled
public class PreferredLights extends LinearOpMode {

	/* Declare OpMode members. */
	HardwarePushbotTest robot   = new HardwarePushbotTest();   // Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	int position=1;
	@Override
	public void runOpMode() {

		/*
		 * Initialize the drive system variables.
		 * The init() method of the hardware class does all the work here
		 */
		robot.init(hardwareMap);

		waitForStart();

		while(opModeIsActive()) {

			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(position-1));
if(gamepad1.x){position=22;
}
if(gamepad1.y){
	position=20;
}
			if(gamepad1.dpad_up){
				position=16;

}
if(gamepad1.a)
	position=45;
			if(gamepad1.b)
				position=2;
if(gamepad1.dpad_down){
				position=75;

}
telemetry.addData("position", position);
			telemetry.update();

		}

	}

}