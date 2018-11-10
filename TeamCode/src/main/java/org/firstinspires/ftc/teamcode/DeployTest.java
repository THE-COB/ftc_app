package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="DeployTest", group="Pushbot")

public class DeployTest extends AvesAblazeOpmode{
//
	/* Declare OpMode members. */

	AvesAblazeHardware robot   = new AvesAblazeHardware();   // Use a Pushbot's hardware
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	int liftHeight;
	double sd;
	String color;
	Orientation angles;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		calibrate();
		telemetry.addData("calibrated", "yes");
		telemetry.update();
		waitForStart();
		deploy();
	}
	public void printStuff(){
		if (resetCoordinates()) {
			telemetry.addData("Target", robot.currentTrackable.getName());
			// express position (translation) of robot in inches.
			robot.translation = robot.lastLocation.getTranslation();
			//ArrayList translation[x, y, z]
			telemetry.addData("x", getX());
			telemetry.addData("y", getY());

			// Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
			robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
			telemetry.addData("Heading", getAngle());
		} else {
			telemetry.addData("Target", "none");
		}
	}
	public String checkColor() {
		if(robot.sensorColor.blue()/Math.pow(20-robot.sensorDistance.getDistance(DistanceUnit.INCH),1)<1.2){
			return "yellow";
		}
		else if(robot.sensorDistance.getDistance(DistanceUnit.INCH)<20){
			return "white";
		}
		else{
			return "nothing";
		}

		/*double[] distances = new double[5];
		robot.stopMotors();
		distances[0] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(0.1);
		sleep(300);
		distances[1] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(-0.1);
		sleep(300);
		distances[2] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(0.1);
		sleep(300);
		distances[3] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
		robot.rotate(-0.1);
		sleep(300);
		distances[4] = robot.sensorDistance.getDistance(DistanceUnit.INCH);
		double s = 0;
		sd = 0;
		robot.stopMotors();


		for (double d : distances) {
			s += d;
		}
		s /= 20;
		for (double d : distances) {
			sd += (Math.abs(s - d));
		}

			if (s > 0) {
				if (sd > 16) {
					return "yellow";
				} else {
					return "white";
				}
			}
			else{
			return "none";
			}*/
	}



}