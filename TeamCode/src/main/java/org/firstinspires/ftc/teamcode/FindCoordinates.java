package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="Find Sample Field", group="Pushbot")

public class FindCoordinates extends LinearOpMode {

	/* Declare OpMode members. */
	AvesAblazeHardwarePushbot robot   = new AvesAblazeHardwarePushbot();   // Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	int liftHeight;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		telemetry.addData("status", "ready");
		telemetry.update();
		liftHeight=robot.getLiftHeight();
		waitForStart();
		sleep(1);
		/*while(Math.abs(liftHeight-robot.getLiftHeight())<3450&&!gamepad1.a){
			robot.lift("up");
			telemetry.addData("liftHeight", robot.getLiftHeight());
			telemetry.addData("startingHeight", liftHeight);
		}
		robot.lift("stop");
		sleep(100);*/
		robot.moveLeftRight(-0.75);
		sleep(250);
		robot.moveUpDown(1);
		sleep(100);
		robot.stopMotors();
		sleep(100);
		/*liftHeight=robot.getLiftHeight();
		while(Math.abs(liftHeight-robot.getLiftHeight())<3300&&!gamepad1.a){
			robot.lift("down");
		}
		robot.lift("stop");*/
		robot.moveUpDown(1);
		sleep(250);
		robot.moveLeftRight(-0.3);
		sleep(500);
		if(!robot.resetCoordinates()){
			robot.moveLeftRight(-0.3);
			sleep(200);
		}
		if(!robot.resetCoordinates()){
			robot.moveLeftRight(-0.3);
			sleep(200);
		}
		if(!robot.resetCoordinates()){
			robot.moveLeftRight(-0.3);
			sleep(200);
		}
		if(!robot.resetCoordinates()){
			robot.moveLeftRight(-0.3);
			sleep(200);
		}
//rotate to 45
		Orientation rotation;
		/*rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
		while(rotation.thirdAngle<130||rotation.thirdAngle>140){
			robot.rotate(0.3);
		}
		robot.stopMotors();*/

//Move to (-48,-15)
		VectorF translation;
		/* translation = robot.lastLocation.getTranslation();
		while(robot.resetCoordinates()&&(translation.get(0)>-46||translation.get(1)>-12)&&opModeIsActive()){
			 translation = robot.lastLocation.getTranslation();
			 robot.moveLeftRight(0.3);
		}
		robot.stopMotors();*/



		while (opModeIsActive()) {
			telemetry.update();
			telemetry.addData("height", robot.getLiftHeight());
			//Display coordinates and trackable
			if (robot.resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", translation.get(0) / AvesAblazeHardwarePushbot.mmPerInch);
				telemetry.addData("y", translation.get(1) / AvesAblazeHardwarePushbot.mmPerInch);

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", rotation.thirdAngle);
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}
}
