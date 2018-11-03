package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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



		while (opModeIsActive()) {
			double s = 0;
			if (robot.sensorDistance.getDistance(DistanceUnit.INCH)<15){
				double[] distances = new double[20];
				do {
					int counter = 0;
					distances[counter] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
				}
				while (distances.length < 20);

				for (double d : distances) {
					s += d;
				}
			}
			if(s>0) {
				if (s / 20 > 0.5) {
					telemetry.addData("color", "yellow");
				} else {
					telemetry.addData("color", "white");
				}
			}
			telemetry.update();
			telemetry.addData("height", robot.getLiftHeight());
			//Display coordinates and trackable
			if (robot.resetCoordinates()) {
				telemetry.addData("Target", robot.currentTrackable.getName());
				// express position (translation) of robot in inches.
				robot.translation = robot.lastLocation.getTranslation();
				//ArrayList translation[x, y, z]
				telemetry.addData("x", robot.translation.get(0) / robot.mmPerInch);
				telemetry.addData("y", robot.translation.get(1) / robot.mmPerInch);

				// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
				Orientation rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				telemetry.addData("Heading", rotation.thirdAngle);
			} else {
				telemetry.addData("Target", "none");
			}


		}
	}
}
