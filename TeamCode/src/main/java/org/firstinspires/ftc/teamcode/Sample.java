package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@Autonomous(name="Sample", group="Pushbot")
@Disabled
public class Sample extends LinearOpMode {

	/* Declare OpMode members. */
	AvesAblazeHardwarePushbot robot   = new AvesAblazeHardwarePushbot();   // Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	int liftHeight;
	String goldMineral;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		telemetry.addData("status", "ready");
		telemetry.update();
		waitForStart();

		double[] distances = new double[5];
		robot.stopMotors();
		sleep(1000);
		distances[0]=robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(0.1);
		sleep(400);
		distances[1]=robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(-0.1);
		sleep(400);
		distances[2]=robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(0.1);
		sleep(300);
		distances[3]=robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(-0.1);
		sleep(300);
		distances[4]=robot.sensorDistance.getDistance(DistanceUnit.INCH);
		double s = 0;
		double sd=0;


		for (double d : distances) {
			s += d;
		}
		s/=20;
		for(double d:distances){
			sd+=(Math.abs(s-d));
		}

		while (opModeIsActive()) {

			if(s>0) {
				if (sd > 11) {
					telemetry.addData("color", "yellow");
				} else {
					telemetry.addData("color", "white");
				}
			}
			telemetry.addData("standard deviation", sd);
			telemetry.update();
			telemetry.addData("height", robot.getLiftHeight());

			//Display coordinates and trackable
			if (robot.resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				robot.translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", robot.translation.get(0) / AvesAblazeHardwarePushbot.mmPerInch);
				telemetry.addData("y", robot.translation.get(1) / AvesAblazeHardwarePushbot.mmPerInch);

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				Orientation rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", rotation.thirdAngle);
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}
}
