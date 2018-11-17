package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="BasicDepot", group="Competition")

public class BasicDepot extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	// Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	public float moveY;
	public float moveX;
	public float rotate;
	public int liftHeight = 0;
	public double sd;
	public String color;
	public Orientation angles;

	@Override
	public void runOpMode() {
		telemetry.addData("status", "calibrating");
		telemetry.update();
		robot.init(hardwareMap);
		telemetry.clearAll();

		calibrate();
		while(robot.imu1.isGyroCalibrated()&&opModeIsActive()){

		}
		telemetry.addData("status", "ready");
		telemetry.update();
			waitForStart();
		//Deploys the robot down from when it is at the starting position
		robot.arm.setPower(-6);
		try{
			sleep(400);
		}
		catch(Exception e){
			stopMotors();
		}
		robot.arm.setPower(0);
		while(Math.abs(liftHeight-getLiftHeight())<3600&&!gamepad1.a&&opModeIsActive()){	//Change to 3600
			lift("up");
			telemetry.clearAll();
			telemetry.addData("liftHeight", getLiftHeight());
			telemetry.addData("startingHeight", liftHeight);
			telemetry.update();
		}
		lift("stop");

		//Once the robot reaches the floor it moves to the left and goes up. It then resets the linear slide
		moveLeftRight(-0.75);
		try{
			sleep(250);
		}
		catch(Exception e)

		{
			stopMotors();
		}
		moveUpDown(1);
		try{
			sleep(100);
		}
		catch(Exception e){

			stopMotors();
		}
		stopMotors();
		liftHeight=getLiftHeight();
		lift("down");
		moveUpDown(1);
		try{
			sleep(340);
		}
		catch(Exception e){
			stopMotors();
		}
		stopMotors();
		while(Math.abs(liftHeight-getLiftHeight())<2900&&!gamepad1.a&&opModeIsActive()){
			lift("down");
		}
		lift("stop");

		stopMotors();
		moveLeftRight(-1);
		try{
			sleep(700);
		}
		catch(Exception e){
			stopMotors();
		}
		//If the robot is unable to find the Vuforia it continues to rotate
		while(!resetCoordinates()&&opModeIsActive()){
			rotate(-0.1);
			try{
				sleep(200);
			}
			catch(Exception e){
				stopMotors();
			}
			stopMotors();
			try{
				sleep(200);
			}
			catch(Exception e){
				stopMotors();
			}
		}
		stopMotors();
		telemetry.clearAll();

		robot.startingAngle=45;
		drive(47, true, 1);
		rotateToAngle(90);
		robot.marker1.setPosition(0.3);
		sleep(800);
		robot.marker1.setPosition(1);
		drive(15,true,1);
		rotateToAngle(176);
		drive(65, true, 0.8);
		stopMotors();
		while (opModeIsActive()){
			telemetry.addData("color", color);
			telemetry.update();
			 angles   = robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			telemetry.addData("angle", angles.firstAngle);
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
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}



}
