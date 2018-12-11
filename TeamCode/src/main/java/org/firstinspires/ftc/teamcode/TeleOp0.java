package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="Rohan's Garbage Teleop", group="Competition")
@Disabled
public class TeleOp0 extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double position=0.8;
	double startingPosition;
	int goldMinerals;
	int silverMinerals;
	boolean allDirDrive = false;
	boolean relativeDrive = false;

	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		//position=robot.extension.getPosition();
		//startingPosition=robot.extension.getPosition();
		waitForStart();
		robot.tfod.activate();
		while(opModeIsActive()) {
            //Move Robot
			moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
			moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
			rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*

			PUT FINAL CONTROL SCHEME HERE


			 */
			if(!allDirDrive && !relativeDrive) {
				if ((Math.abs(moveY) > 0.25)) {
					moveUpDown(moveY);

				} else if (Math.abs(moveX) > 0.25)
					moveLeftRight(-moveX);
				else if (Math.abs(rotate) > 0.25) {
					rotate(-rotate);
				} else if (gamepad1.left_bumper) {
					rotate(0.1);
				} else if (gamepad1.right_bumper) {
					rotate(-0.1);
				} else if (gamepad1.dpad_up) {
					moveUpDown(0.3);
				} else if (gamepad1.dpad_down) {
					moveUpDown(-0.3);
				} else if (gamepad1.dpad_left) {
					moveLeftRight(-0.3);
				} else if (gamepad1.dpad_right) {
					moveLeftRight(0.3);
				} else {
					stopMotors();
				}
			}
			else if(relativeDrive){

			}
			else{
				if(Math.abs(moveX)>0.25 || Math.abs(moveY)>0.25) {
					if(moveX == 0){
						moveUpDown(moveY);
					}
					else if(moveY == 0){
						moveLeftRight(moveX);
					}
					else {
						polarDrive(Math.abs(Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2))), Math.atan(moveY / moveX));
					}
				}
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

			if(gamepad2.right_bumper){
					robot.lid.setPosition(0.55);

			}
			else if(gamepad2.left_bumper){
				robot.lid.setPosition(0.9);
			}


			if(gamepad2.dpad_up){
				lift("up");
			}
			else if (gamepad2.dpad_down){
				lift("down");
			}
			else if (gamepad1.y){
				lift("up");
			}
			else if (gamepad1.a && !gamepad1.start){
				lift("down");
			}
			else{
				lift("stop");
			}

			telemetry.addData("position", position);
			//telemetry.addData("actual position", robot.extension.getPosition());
			telemetry.addData("starting position", startingPosition);
			//robot.extension.setPosition(Range.clip(position,startingPosition,startingPosition+0.92));

			if(gamepad2.x){
				position=startingPosition;
				//robot.extension.setPosition(position);
			//	while (opModeIsActive() && gamepad1.x) ;
			}
			else if(gamepad2.b&&!gamepad2.start) {
				position =startingPosition+0.92;
				//robot.extension.setPosition(position);
			//	while (opModeIsActive() && gamepad1.b) ;
			}
			else if(gamepad2.y){
				position+=0.01;
			}
			else if(gamepad2.a&&!gamepad2.start){
				position-=0.01;
			}

			if(Math.abs(gamepad2.left_stick_y)>0.1)
			robot.arm.setPower(gamepad2.left_stick_y/1.5);
			else if(gamepad2.left_trigger>0.1){
				robot.arm.setPower(gamepad2.left_trigger/3.25);
			}
			else if(gamepad2.right_trigger>0.1){
				robot.arm.setPower(-gamepad2.right_trigger/3.25);
			}
			else
				robot.arm.setPower(0);

			//Both controllers press back, start, and both bumpers
			if(gamepad1.back && gamepad1.start && gamepad1.left_bumper && gamepad1.right_bumper && gamepad2.back && gamepad2.start && gamepad2.left_bumper && gamepad2.right_bumper){
				allDirDrive = true;
			}

			if(gamepad1.back && gamepad1.start && gamepad1.left_trigger>0.5 && gamepad1.right_trigger>0.5 && gamepad2.back && gamepad2.start && gamepad2.left_trigger>0.5 && gamepad2.right_trigger>0.5){
				relativeDrive = true;
			}
		}


	}

}
