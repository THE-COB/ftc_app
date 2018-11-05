package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Find inches", group = "pushbot")
public class FindInches extends LinearOpMode {

	AvesAblazeHardwarePushbot robot   = new AvesAblazeHardwarePushbot();

	@Override
	public void runOpMode() {
		robot.moveUpDown(0.25,20);
	}
}
