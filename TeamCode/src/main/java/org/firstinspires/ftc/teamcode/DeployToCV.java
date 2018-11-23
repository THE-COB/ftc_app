package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
@TeleOp(name="DeployToCV", group="Competition")

public class DeployToCV extends AvesAblazeOpmode {

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
		robot.arm.setPower(1);
		try{
			sleep(400);
		}
		catch(Exception e){
			stopMotors();
		}
		robot.arm.setPower(0);

		deploy();
		polarDrive(0.5,2.7);
		try {
			sleep(2200);
		} catch (Exception e) {
			stopMotors();
		}
		stopMotors();
		while(!resetCoordinates())
			polarDrive(0.2, -2.7);
		moveToCoord(-60,-3,10,0.25);

		checkMinerals();

		telemetry.addData("CorrectPosition",position);
		telemetry.update();

		/*robot.startingAngle=45;
		drive(47, true, 1);
		rotateToAngle(90);
		robot.marker1.setPosition(0.3);
		sleep(800);
		robot.marker1.setPosition(1);
		drive(15,true,1);
		rotateToAngle(176);
		robot.marker1.setPosition(0.3);
		sleep(800);
		robot.marker1.setPosition(1);
		drive(68, true, 0.8);*/
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
