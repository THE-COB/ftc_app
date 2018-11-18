package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="ArmTest", group="Competition")

public class ArmTest extends AvesAblazeOpmode {

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
		//calibrate();
		telemetry.addData("status", "ready");
		telemetry.update();
		waitForStart();
		//Deploys the robot down from when it is at the starting position
		int armPosition = robot.arm.getCurrentPosition();
		while (Math.abs(armPosition - robot.arm.getCurrentPosition()) < 50&&opModeIsActive()){
			telemetry.addData("postion", robot.arm.getCurrentPosition());
			telemetry.addData("oldPosition", armPosition);
			telemetry.update();
			robot.arm.setPower(1);
		}

		robot.arm.setPower(0);
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
