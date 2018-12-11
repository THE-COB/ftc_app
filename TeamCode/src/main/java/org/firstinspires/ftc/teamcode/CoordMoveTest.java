package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="CoordMoveTest", group="Competition")

public class CoordMoveTest extends AvesAblazeOpmode {

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
		robot.startingAngle=getAngle();
		calibrate();
		waitForStart();
		while(!resetCoordinates()||!robot.imu1.isGyroCalibrated()&&!gamepad1.a);
		try {
			moveToCoord(-59,-4,135,0.25);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sleep(1000);
		try {
			moveToCoord(-27,26,141,0.4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			moveToCoord(getX()+5,getY()+7,getAngle(),0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sleep(1000);
		try {
			moveToCoord(getX()-5,getY()-5,getAngle(),0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sleep(1000);
		try {
			moveToCoord(getX()+5,getY(),getAngle(),0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sleep(1000);
		try {
			moveToCoord(getX(),getY()-5,getAngle(),0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
