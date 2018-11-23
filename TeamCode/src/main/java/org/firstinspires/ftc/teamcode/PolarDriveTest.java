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
			if(gamepad1.dpad_right&&gamepad1.dpad_up){
				polarDrive(0.5,Math.PI/4);
			}
			else if(gamepad1.dpad_right&&gamepad1.dpad_down){
				polarDrive(0.5,-Math.PI/4);
			}
			else if(gamepad1.dpad_left&&gamepad1.dpad_down){
				polarDrive(0.5,-3*Math.PI/4);
			}
			else if(gamepad1.dpad_left&&gamepad1.dpad_up){
				polarDrive(0.5,3*Math.PI/4);
			}
			else if(gamepad1.dpad_right){
				polarDrive(0.5,0);
			}
			else if(gamepad1.dpad_up){
				polarDrive(0.5,Math.PI/2);
			}
			else if(gamepad1.dpad_down){
				polarDrive(0.5,-Math.PI/2);
			}
			else if(gamepad1.dpad_left){
				polarDrive(0.5,Math.PI);
			}
			else{
				stopMotors();
			}
		}


	}
public void polarDrive(double power, double angle){
	angle=angle-(Math.PI/4);
	robot.motor0.setPower(power*Math.cos(angle));
	robot.motor1.setPower(power*Math.sin(angle));
	robot.motor3.setPower(power*-Math.cos(angle));
	robot.motor2.setPower(power*-Math.sin(angle));
}
}
