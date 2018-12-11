package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="AvesAblazeTeleop", group="Competition")
//Rohan Don't touch this I swear to God
public class OldTeleop extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	double startingPosition=0;
	int position;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(60));
		waitForStart();
		runtime.reset();
		position=57;
		while(opModeIsActive()) {
			telemetry.addData("time",Math.round(runtime.seconds()));
			telemetry.addData("armLength", robot.extension.getCurrentPosition());
			telemetry.addData("liftHeight", robot.lift1.getCurrentPosition());
			telemetry.update();
			if(runtime.seconds()>120){
				position=97;
			}
			else if(runtime.seconds()>115){
				position=45;
			}
			else if(runtime.seconds()>105){
				if(Math.round(runtime.seconds()*4)%2==0)
				position=96;
				else
					position=100;
			}
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(position-1));
			//Move Robot
			moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
			moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
			rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*
			PUT FINAL CONTROL SCHEME HERE


			 */

			if ((Math.abs(moveY) > 0.25)) {
				moveUpDown(-moveY);
				position=57;

			}
			else if (Math.abs(moveX) > 0.25) {
				moveLeftRight(-moveX);
				position=67;
			}
			else if (Math.abs(rotate) > 0.25) {
				rotate(-rotate);
				if(Math.round(runtime.seconds()*8)%2==0){
					position=3;
				}
				else{
					position=100;
				}
				/*if(Math.round(runtime.seconds()*6)%6==0)
					position=68;
				else if(Math.round(runtime.seconds()*6+1)%6==0){
					position=58;
				}
				else if(Math.round(runtime.seconds()*6+2)%6==0){
					position=46;
				}
				else if(Math.round(runtime.seconds()*6+3)%6==0){
					position=6;
				}
				else if(Math.round(runtime.seconds()*4)%2==0){
					position=48;
				}
				else{
					position=45;
				}*/
			}
			else if (gamepad1.left_bumper){
				rotate(-0.1);
				position=39;
			}
			else if (gamepad1.right_bumper){
				rotate(0.1);
				position=39;
			}
			else if(gamepad1.dpad_up){
				position=64;
				moveUpDown(-0.3);
			}
			else if(gamepad1.dpad_down){
				position=64;
				moveUpDown(+0.3);
			}
			else if(gamepad1.dpad_left){
				position=54;
				moveLeftRight(-0.3);
			}
			else if(gamepad1.dpad_right){
				position=54;
				moveLeftRight(0.3);
			}
			else{
				position=72;
				stopMotors();
			}

			//Move team marker mechanism
			if(gamepad1.left_trigger>0.1){
				robot.marker1.setPosition(1);
			}
			if(gamepad1.right_trigger>0.1){
				robot.marker1.setPosition(0.3);
			}

			else{
				//robot.marker.setPower(0.6);
			}

			//lift robot
			if(gamepad2.left_bumper){
				position=97;
					robot.lid.setPosition(0.9);

			}
			else if(gamepad2.right_bumper){
				robot.lid.setPosition(0.55);
				if(runtime.seconds()<110) {
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
					sleep(100);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
				}
			}

			if(gamepad2.dpad_up){
				lift("up");
				position=81;
			}
			else if (gamepad2.dpad_down){
				lift("down");
				position=81;
			}
			else if (gamepad1.y){
				lift("up");
				position=81;
			}
			else if (gamepad1.a && !gamepad1.start){
				lift("down");
				position=81;
			}
			else{
				lift("stop");
			}



			if(gamepad2.x){
				//extends
				extensionPosition=startingPosition;
				robot.extension.setPower(0.5);
			//	while (opModeIsActive() && gamepad1.x) ;
			}
			else if(gamepad2.b&&!gamepad2.start) {
				//retracts
				robot.extension.setPower(-0.5);
			//	while (opModeIsActive() && gamepad1.b) ;
			}
			else if(gamepad2.y){
				extend();
			}
			else if(gamepad2.a&&!gamepad2.start){

				retract();
			}
			else{
				robot.extension.setPower(0);
			}

			if(Math.abs(gamepad2.left_stick_y)>0.1) {
				robot.arm.setPower(gamepad2.left_stick_y / 1.5);
				position=22;
			}
			else if(gamepad2.left_trigger>0.1){
				robot.arm.setPower(gamepad2.left_trigger/3.25);
				position=22;
			}
			else if(gamepad2.right_trigger>0.1){
				robot.arm.setPower(-gamepad2.right_trigger/3.25);
				position=22;
			}
			else
				robot.arm.setPower(0);
		}


	}

}
