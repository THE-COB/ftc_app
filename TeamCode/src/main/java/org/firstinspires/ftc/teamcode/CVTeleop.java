package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="CVTeleop", group="Competition")

public class CVTeleop extends AvesAblazeOpmode {

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
		position=robot.extension.getPosition();
		startingPosition=robot.extension.getPosition();
		waitForStart();
		robot.tfod.activate();
		while(opModeIsActive()) {
			telemetry.clearAll();
			if (robot.tfod != null) {
				// getUpdatedRecognitions() will return null if no new information is available since
				// the last time that call was made.
				List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();
				if (updatedRecognitions != null) {
					telemetry.addData("# Object Detected", updatedRecognitions.size());
					if (updatedRecognitions.size() == 3) {
						int goldMineralX = -1;
						int silverMineral1X = -1;
						int silverMineral2X = -1;
						for (Recognition recognition : updatedRecognitions) {
							if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
								goldMineralX = (int) recognition.getLeft();
							} else if (silverMineral1X == -1) {
								silverMineral1X = (int) recognition.getLeft();
							} else {
								silverMineral2X = (int) recognition.getLeft();
							}
						}
						if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
							if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
								telemetry.addData("Gold Mineral Position", "Left");
							} else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
								telemetry.addData("Gold Mineral Position", "Right");
							} else {
								telemetry.addData("Gold Mineral Position", "Center");
							}
						}
					}
					telemetry.update();
				}

			}
			telemetry.addData("height", getLiftHeight());
			//Display coordinates and trackable
			if (resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				robot.translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", getX());
				telemetry.addData("y", getY());

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", robot.rotation.thirdAngle);
				telemetry.update();
			} else {
				telemetry.addData("Target", "none");
				telemetry.update();
			}



            //Move Robot
			moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
			moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
			rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*

			PUT FINAL CONTROL SCHEME HERE


			 */
			if ((Math.abs(moveY) > 0.25)) {
				moveUpDown(moveY);

			}
			else if (Math.abs(moveX) > 0.25)
				moveLeftRight(-moveX);
			else if (Math.abs(rotate) > 0.25) {
				rotate(-rotate);
			}
			else if (gamepad1.left_bumper){
				rotate(0.1);
			}
			else if (gamepad1.right_bumper){
				rotate(-0.1);
			}
			else if(gamepad1.dpad_up){
				moveUpDown(0.3);
			}
			else if(gamepad1.dpad_down){
				moveUpDown(-0.3);
			}
			else if(gamepad1.dpad_left){
				moveLeftRight(-0.3);
			}
			else if(gamepad1.dpad_right){
				moveLeftRight(0.3);
			}
			else{
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
			telemetry.addData("actual position", robot.extension.getPosition());
			telemetry.addData("starting position", startingPosition);
			robot.extension.setPosition(Range.clip(position,startingPosition,startingPosition+0.92));

			if(gamepad2.x){
				position=startingPosition;
				robot.extension.setPosition(position);
			//	while (opModeIsActive() && gamepad1.x) ;
			}
			else if(gamepad2.b&&!gamepad2.start) {
				position =startingPosition+0.92;
				robot.extension.setPosition(position);
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

		}


	}

}
