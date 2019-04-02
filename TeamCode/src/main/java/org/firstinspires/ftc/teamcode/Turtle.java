package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


/**
 * Created by Rohan Mathur on 3/25/19.
 */

@Autonomous(name = "Turtle", group = "AAA")

public class Turtle extends AvesAblazeOpmode {


	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		if (opModeIsActive()) {
			moveUpDown(1);
			sleep(1000);
			rotate(0.5);
			sleep(500);
			moveUpDown(1);
			rotate(0.5);
			moveUpDown(1);
			rotate(0.5);
			moveUpDown(1);
			sleep(500);
			stopMotors();

		}
	}

}
