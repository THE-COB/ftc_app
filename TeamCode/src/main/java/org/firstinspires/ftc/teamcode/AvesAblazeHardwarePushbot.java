package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * Created by Rohan Mathur on 9/17/18.
 */
public class AvesAblazeHardwarePushbot {
	private static final String VUFORIA_KEY = "ASre9vb/////AAABmS9qcsdgiEiVmAClC8R25wUqiedmCZI33tlr4q8OswrB3Kg7FKhhuQsUv3Ams+kaXnsjj4VxJlgsopgZOhophhcKyw6VmXIFChkIzZmaqF/PcsDLExsXycCjm/Z/LWQEdcmuNKbSEgc1sTAwKyLvWn6TK+ne1fzboxjtTmkVqu/lBopmR3qI+dtd3mjYIBiLks9WW6tW9zS4aau7fJCNYaU1NPgXfvq1CRjhWxbX+KWSTUtYuFSFUBw2zI5PzIPHaxKrIwDKewo1bOZBUwbqzmm5h0d4skXo3OC0r+1AYrMG0HJrGRpkN9U6umTlYd5oWCqvgBSVxKkOGM1PhNY5cX+sqHpbILgP+QVOFblKSV9i";
	VuforiaLocalizer vuforia;// Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.

	DcMotor motor0;
	DcMotor motor1;
	DcMotor motor2;
	DcMotor motor3;

	private DcMotor lift1;
	private DcMotor lift2;

	BNO055IMU imu1;
	ColorSensor sensorColor;
	DistanceSensor sensorDistance;
	int startingHeight;

	Servo marker1;
	Servo marker2;

	// We will define some constants and conversions here
	public static final float mmPerInch        = 25.4f;
	public static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
	public static final float mmTargetHeight   = (6) * mmPerInch;
	public static double angle=0;
	int startingAngle=0;
	// the height of the center of the target image above the floor
	VuforiaTrackables targetsRoverRuckus;
	List<VuforiaTrackable> allTrackables;
	VuforiaTrackable currentTrackable;
	// Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
	// Valid choices are:  BACK or FRONT
	public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

	public OpenGLMatrix lastLocation = null;
	public boolean targetVisible = false;

	public Orientation rotation;
	public VectorF translation;
	Orientation angles;

	HardwareMap hwMap;

	/* Initialize standard Hardware interfaces */
	public void init(HardwareMap ahwMap) {

		hwMap=ahwMap;
		marker1=hwMap.get(Servo.class, "marker1");
		marker2=hwMap.get(Servo.class, "marker2");
		marker1.setPosition(1);



		imu1=hwMap.get(BNO055IMU.class, "imu 1");
		// get a reference to the color sensor.
		sensorColor = hwMap.get(ColorSensor.class, "colorRange");

		// get a reference to the distance sensor that shares the same name.
		sensorDistance = hwMap.get(DistanceSensor.class, "colorRange");
		/*
			MOTORS AT FULL POWER ALL MOVING FORWARD MOVE AT 2.618 ft/sec
		*/

		motor0 = hwMap.get(DcMotor.class, "motor0");
		motor0.setDirection(DcMotor.Direction.FORWARD);
		motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor1 = hwMap.get(DcMotor.class, "motor1");
		motor1.setDirection(DcMotor.Direction.FORWARD);
		motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor2 = hwMap.get(DcMotor.class, "motor2");
		motor2.setDirection(DcMotor.Direction.FORWARD);
		motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motor3 = hwMap.get(DcMotor.class, "motor3");
		motor3.setDirection(DcMotor.Direction.FORWARD);
		motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		lift1 = hwMap.get(DcMotor.class, "lift1");
		lift1.setDirection(DcMotor.Direction.FORWARD);
		lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		lift2 = hwMap.get(DcMotor.class, "lift2");
		lift2.setDirection(DcMotor.Direction.REVERSE);
		lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		startingHeight=lift1.getCurrentPosition();

		int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
		VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


		parameters.vuforiaLicenseKey = VUFORIA_KEY ;
		parameters.cameraDirection   = CAMERA_CHOICE;

		//  Instantiate the Vuforia engine
		vuforia = ClassFactory.getInstance().createVuforia(parameters);

		// Load the data sets that for the trackable objects. These particular data
		// sets are stored in the 'assets' part of our application.
		targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
		VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
		blueRover.setName("Blue-Rover");
		VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
		redFootprint.setName("Red-Footprint");
		VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
		frontCraters.setName("Front-Craters");
		VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
		backSpace.setName("Back-Space");

		// For convenience, gather together all the trackable objects in one easily-iterable collection */
		allTrackables = new ArrayList<VuforiaTrackable>();
		allTrackables.addAll(targetsRoverRuckus);

		/**
		 * In order for localization to work, we need to tell the system where each target is on the field, and
		 * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
		 * Transformation matrices are a central, important concept in the math here involved in localization.
		 * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
		 * for detailed information. Commonly, you'll encounter transformation matrices as instances
		 * of the {@link OpenGLMatrix} class.
		 *
		 * If you are standing in the Red Alliance Station looking towards the center of the field,
		 *     - The X axis runs from your left to the right. (positive from the center to the right)
		 *     - The Y axis runs from the Red Alliance Station towards the other side of the field
		 *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
		 *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
		 *
		 * This Rover Ruckus sample places a specific target in the middle of each perimeter wall.
		 *
		 * Before being transformed, each target image is conceptually located at the origin of the field's
		 *  coordinate system (the center of the field), facing up.
		 */

		/**
		 * To place the BlueRover target in the middle of the blue perimeter wall:
		 * - First we rotate it 90 around the field's X axis to flip it upright.
		 * - Then, we translate it along the Y axis to the blue perimeter wall.
		 */
		OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
				.translation(0, mmFTCFieldWidth, mmTargetHeight)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
		blueRover.setLocation(blueRoverLocationOnField);

		/**
		 * To place the RedFootprint target in the middle of the red perimeter wall:
		 * - First we rotate it 90 around the field's X axis to flip it upright.
		 * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
		 *   and facing inwards to the center of the field.
		 * - Then, we translate it along the negative Y axis to the red perimeter wall.
		 */
		OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
				.translation(0, -mmFTCFieldWidth, mmTargetHeight)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
		redFootprint.setLocation(redFootprintLocationOnField);

		/**
		 * To place the FrontCraters target in the middle of the front perimeter wall:
		 * - First we rotate it 90 around the field's X axis to flip it upright.
		 * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
		 *   and facing inwards to the center of the field.
		 * - Then, we translate it along the negative X axis to the front perimeter wall.
		 */
		OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
				.translation(-mmFTCFieldWidth, 0, mmTargetHeight)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
		frontCraters.setLocation(frontCratersLocationOnField);

		/**
		 * To place the BackSpace target in the middle of the back perimeter wall:
		 * - First we rotate it 90 around the field's X axis to flip it upright.
		 * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
		 *   and facing inwards to the center of the field.
		 * - Then, we translate it along the X axis to the back perimeter wall.
		 */
		OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
				.translation(mmFTCFieldWidth, 0, mmTargetHeight)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
		backSpace.setLocation(backSpaceLocationOnField);

		/**
		 * Create a transformation matrix describing where the phone is on the robot.
		 *
		 * The coordinate frame for the robot looks the same as the field.
		 * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
		 * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
		 *
		 * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
		 * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
		 * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
		 *
		 * If using the rear (High Res) camera:
		 * We need to rotate the camera arouBACKnd it's long axis to bring the rear camera forward.
		 * This requires a negative 90 degree rotation on the Y axis
		 *
		 * If using the Front (Low Res) camera
		 * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
		 * This requires a Positive 90 degree rotation on the Y axis
		 *
		 * Next, translate the camera lens to where it is on the robot.
		 * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
		 */

		final int CAMERA_FORWARD_DISPLACEMENT  = 0;   // eg: Camera is 110 mm in front of robot center
		final int CAMERA_VERTICAL_DISPLACEMENT = 0;   // eg: Camera is 200 mm above ground
		final int CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

		OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
				.translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
						CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

		/**  Let all the trackable listeners know where the phone is.  */
		for (VuforiaTrackable trackable : allTrackables)
		{
			((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
		}

		// Save reference to Hardware map
		hwMap = ahwMap;

	}


	public  void stopMotors(){
		motor0.setPower(0);
		motor1.setPower(0);
		motor2.setPower(0);
		motor3.setPower(0);
	}
	public void rotate(double power){
		motor0.setPower(-power);
		motor1.setPower(-power);
		motor2.setPower(-power);
		motor3.setPower(-power);
	}
	public void moveLeftRight(double power){
		//int angle = getAngle();
		motor0.setPower(-power);
		motor1.setPower(-power);
		motor2.setPower(power);
		motor3.setPower(power);
		/*if(angle != getAngle()){
			rotateToAngle(angle);
		}*/
	}
	public void moveUpDown(double power){
		motor0.setPower(-power);
		motor1.setPower(power);
		motor2.setPower(-power);
		motor3.setPower(power);
	}
	public void rotate(double power, int tics){
		if(tics < 0) power = power*-1;
		int initPos = motor0.getCurrentPosition();
		int currPos = initPos;
		while(currPos != initPos+tics){
			motor0.setPower(-power);
			motor1.setPower(-power);
			motor2.setPower(-power);
			motor3.setPower(-power);
			currPos = motor0.getCurrentPosition();
		}
		stopMotors();
	}
	public void moveLeftRight(double power, int tics){
		int angle = getAngle();
		if(tics<0) power = power*-1;
		int initPos = motor0.getCurrentPosition();
		int currPos = initPos;
		while(Math.abs(currPos) < initPos+tics){
			motor0.setPower(-power);
			motor1.setPower(-power);
			motor2.setPower(power);
			motor3.setPower(power);
			currPos = motor0.getCurrentPosition();
		}
		stopMotors();
	}
	public void moveUpDown(double power, int tics){
		if(tics<0) power = power*-1;
		int initPos = (Math.abs(motor0.getCurrentPosition())+Math.abs(motor1.getCurrentPosition())+Math.abs(motor2.getCurrentPosition())+Math.abs(motor3.getCurrentPosition()))/4;
		int currPos = initPos;
		while(Math.abs(currPos) < initPos+tics){
			motor0.setPower(power);
			motor1.setPower(-power);
			motor2.setPower(power);
			motor3.setPower(-power);
			currPos = (Math.abs(motor0.getCurrentPosition())+Math.abs(motor1.getCurrentPosition())+Math.abs(motor2.getCurrentPosition())+Math.abs(motor3.getCurrentPosition()))/4;

		}
		stopMotors();
	}

	public void drive(double inches, boolean forward){
		if(forward)
			moveUpDown(-0.8,(int)Math.round(45*inches));
		else{
			moveUpDown(0.8,(int)Math.round(45*inches));
		}

	}
	public void moveAll(double xVal, double yVal) {

	}
	public int getMotorVal(int motorNum){
		switch (motorNum){
			case 0: return motor0.getCurrentPosition();
			case 1: return motor1.getCurrentPosition();
			case 2: return motor2.getCurrentPosition();
			case 3: return motor3.getCurrentPosition();
			default: return 0;
		}
	}
	public boolean resetCoordinates(){
		targetsRoverRuckus.activate();
		targetVisible=false;

			// check all the trackable target to see which one (if any) is visible.
			for (VuforiaTrackable trackable : allTrackables) {
				if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
					targetVisible = true;
					currentTrackable = trackable;
					// getUpdatedRobotLocation() will return null if no new information is available since
					// the last time that call was made, or if the trackable is not currently visible.
					OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
					if (robotLocationTransform != null) {
						lastLocation = robotLocationTransform;

					}
					translation = lastLocation.getTranslation();
					rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
					return targetVisible;
				}
			}
		return targetVisible;
	}

	public int getX(){
		if(!resetCoordinates()) return 10000;
		return Math.round(translation.get(0)/mmPerInch);
	}
	public int getY(){
		if(!resetCoordinates()) return 10000;
		return Math.round(translation.get(1)/mmPerInch);
	}
	public int getAngle(){
		if(!resetCoordinates()&&imu1.isGyroCalibrated()){
			angles=imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			double currentAngle=angles.firstAngle;
			int finalAngle= startingAngle+(int)Math.round(currentAngle);
			return finalAngle;

		}
		else if(resetCoordinates()){
			double oldAngle = rotation.thirdAngle;
			double posAngle = oldAngle;
			int finalAngle;
			if (oldAngle < 0) posAngle = 360 - Math.abs(oldAngle);
			if((int) (Math.round(posAngle)) - 45 < 0){
				finalAngle = 360-(int)Math.round(posAngle);
			}
			else{
				finalAngle = (int) (Math.round(posAngle)) - 45;
			}
			return finalAngle;
		}
		else{
			return 10000;
		}
	}

	public void lift(String direction){
		if(direction.equals("up")){
			lift1.setPower(-1);
			lift2.setPower(-1);
		}
		else if(direction.equals("down")){
			lift1.setPower(1);
			lift2.setPower(1);
		}
		else if(direction.equals("stop")){
			lift1.setPower(0);
			lift2.setPower(0);
		}
	}
	public void rotateToAngle(int newAngle) {
		int diff = newAngle - getAngle();
		if (newAngle == getAngle()) {
			stopMotors();
			return;
		}
		while (Math.abs(getAngle() - newAngle) > 0.7) {
			if ((diff > 0 && diff < 180) || (diff < 0 && Math.abs(diff) > 180)) {

				if (Math.abs(getAngle() - newAngle) > 40) {
					rotate(-0.5);
				}
				else if (Math.abs(getAngle() - newAngle) > 15) {
					rotate(-0.2);
				} else  {
					rotate(-0.1);
				}
			} else {

				if (Math.abs(getAngle() - newAngle) > 40) {
					rotate(0.4);
				}
				else if (Math.abs(getAngle() - newAngle) > 15) {
					rotate(0.2);
				} else {
					rotate(0.1);
				}
			}

			stopMotors();
		}
	}
	public void moveToCoord(int x, int y, int angle){
		int oldAngle = getAngle();
		rotateToAngle(angle);
		while(getX()!=x && getY()!=y){
			moveUpDown(0.25);
		}
		stopMotors();
	}

	public void lift(){
		while(lift1.getCurrentPosition()<3350+startingHeight){
			lift("up");
		}
		lift("stop");
	}
	public void lower(){
		while(lift1.getCurrentPosition()>startingHeight+50){
			lift("down");
		}
		lift("stop");
	}
	public void liftAcc(int tics){
		int initPos = lift1.getCurrentPosition();
		int currPos = initPos;
		while(currPos <= initPos+tics){
			lift1.setPower(0.5);
			lift2.setPower(0.5);
		}
		lift1.setPower(0);
		lift2.setPower(0);
	}

	public void resetEncodes(){
		motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}
	public int getLiftHeight(){
		return lift1.getCurrentPosition();
	}
	public String checkColor() {
		if (sensorDistance.getDistance(DistanceUnit.INCH) < 5.5) {
			return "white";
		} else if (sensorDistance.getDistance(DistanceUnit.INCH) < 20) {
			return "yellow";
		} else {
			return "nothing";
		}

	}
}
