package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * Created by Rohan Mathur on 2/4/19.
 */

@Autonomous(name = "Color/Distance test", group = "Test")
@Disabled
public class colorDistanceTest extends AvesAblazeOpmode  {
	/* Declare OpMode members. */
	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	double startingPosition=0;
	int lightCode;
	boolean allDirDrive = false;
	int relativeDrive = 10000;


	private static final String VUFORIA_KEY = "ASre9vb/////AAABmS9qcsdgiEiVmAClC8R25wUqiedmCZI33tlr4q8OswrB3Kg7FKhhuQsUv3Ams+kaXnsjj4VxJlgsopgZOhophhcKyw6VmXIFChkIzZmaqF/PcsDLExsXycCjm/Z/LWQEdcmuNKbSEgc1sTAwKyLvWn6TK+ne1fzboxjtTmkVqu/lBopmR3qI+dtd3mjYIBiLks9WW6tW9zS4aau7fJCNYaU1NPgXfvq1CRjhWxbX+KWSTUtYuFSFUBw2zI5PzIPHaxKrIwDKewo1bOZBUwbqzmm5h0d4skXo3OC0r+1AYrMG0HJrGRpkN9U6umTlYd5oWCqvgBSVxKkOGM1PhNY5cX+sqHpbILgP+QVOFblKSV9i";

	// Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
	// We will define some constants and conversions here
	private static final float mmPerInch        = 25.4f;
	private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
	private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

	// Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
	// Valid choices are:  BACK or FRONT
	private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

	private OpenGLMatrix lastLocation = null;
	private boolean targetVisible = false;
	VectorF translation= new VectorF(0,0,0);

	/**
	 * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
	 * localization engine.
	 */
	VuforiaLocalizer vuforia;

	@Override
	public void runOpMode() {
		try {
			runtime = new ElapsedTime();
			robot.init(hardwareMap);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(50));
		} catch (Exception e) {
//			tfod.shutdown();
		}
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(60));
		calibrate();
		while (opModeIsActive() && (!robot.imu.isGyroCalibrated() && !robot.imu1.isGyroCalibrated())) {
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(97 - 1));
			calibrate();


		}
		robot.startingAngle=135;
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(1 - 1));
		int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

		// VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

		parameters.vuforiaLicenseKey = VUFORIA_KEY ;
		parameters.cameraDirection   = CAMERA_CHOICE;

		//  Instantiate the Vuforia engine
		vuforia = ClassFactory.getInstance().createVuforia(parameters);

		// Load the data sets that for the trackable objects. These particular data
		// sets are stored in the 'assets' part of our application.
		VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
		VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
		blueRover.setName("Blue-Rover");
		VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
		redFootprint.setName("Red-Footprint");
		VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
		frontCraters.setName("Front-Craters");
		VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
		backSpace.setName("Back-Space");

		// For convenience, gather together all the trackable objects in one easily-iterable collection */
		List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
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
		 * We need to rotate the camera around it's long axis to bring the rear camera forward.
		 * This requires a negative 90 degree rotation on the Y axis
		 *
		 * If using the Front (Low Res) camera
		 * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
		 * This requires a Positive 90 degree rotation on the Y axis
		 *
		 * Next, translate the camera lens to where it is on the robot.
		 * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
		 */

		final int CAMERA_FORWARD_DISPLACEMENT  = 110;   // eg: Camera is 110 mm in front of robot center
		final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
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

		robot.phoneServoX.setPosition(0.33);
		robot.phoneServoY.setPosition(0.46);
		telemetry.addData(">", "Press Play to start tracking");
		telemetry.update();
		waitForStart();

		/** Start tracking the data sets we care about. */
		targetsRoverRuckus.activate();
		waitForStart();
		if (opModeIsActive()) {
			runtime.reset();
			lightCode = 57;
			while (opModeIsActive()) {
				if (gamepad1.dpad_left&&gamepad1.a) {
					robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() - 0.01);
					while (gamepad1.dpad_left);
				}
				if (gamepad1.dpad_right&&gamepad1.a) {
					robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() + 0.01);
					while (gamepad1.dpad_right);
				}

				if (gamepad1.dpad_up&&gamepad1.a) {
					robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() + 0.01);
					while (gamepad1.dpad_up);
				}
				if (gamepad1.dpad_down&&gamepad1.a) {
					robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() - 0.01);
					while (gamepad1.dpad_down);
				}
				telemetry.addData("servoX", robot.phoneServoX.getPosition());
				telemetry.addData("servoY", robot.phoneServoY.getPosition());
				targetVisible = false;
				for (VuforiaTrackable trackable : allTrackables) {
					if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
						telemetry.addData("Visible Target", trackable.getName());
						targetVisible = true;

						// getUpdatedRobotLocation() will return null if no new information is available since
						// the last time that call was made, or if the trackable is not currently visible.
						OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
						if (robotLocationTransform != null) {
							lastLocation = robotLocationTransform;
						}
						break;
					}
				}

				// Provide feedback as to where the robot is located (if we know).
				if (targetVisible) {
					// express position (translation) of robot in inches.
					VectorF translation = lastLocation.getTranslation();
					telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
							translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

					// express the rotation of the robot in degrees.
					Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
					telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
				}
				else {
					telemetry.addData("Visible Target", "none");
				}


				telemetry.addData("time", Math.round(runtime.seconds()));
				telemetry.addData("armLength", robot.extension.getCurrentPosition());
				telemetry.addData("liftHeight", robot.lift1.getCurrentPosition());
				telemetry.addData("armPosition", robot.arm.getCurrentPosition());
				telemetry.addData("angle", getAngle());
				if(gamepad1.b&&gamepad1.dpad_down){
					ElapsedTime scanTime = new ElapsedTime();
					while(position.equals("none")&&opModeIsActive()) {
						finalMinFinder();
						if (Math.round(scanTime.milliseconds() / 200) % 16 == 0) {
							robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() - 0.00055);
							robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() + 0.00035);
						}
					}

					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(9 - 1));
					robot.arm.setPower(1);
					sleep(600);
					robot.arm.setPower(0);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(39 - 1));

					lift();
					telemetry.addData("status", "deployed");
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));

					telemetry.update();
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					sleep(150);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
					sleep(150);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					sleep(150);
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93 - 1));
					rotate(0.5);
					sleep(200);
					moveLeftRight(1);
					sleep(370);
					stopMotors();
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(19 - 1));
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(93 - 1));
					rotate(-0.1);
					sleep(500);
					stopMotors();

					stopMotors();

					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99 - 1));
					robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95 - 1));
					robot.startingAngle = 135;
					rotateToAngle(135);
					if (position.equals("left") || gamepad1.b) {
						polarDrive(1, 2 * Math.PI / 2.97);
						sleep(1420);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
						polarDrive(1, -(Math.PI - (2 * Math.PI / 2.9)));
						sleep(1120);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

					} else if (position.equals("right") || gamepad1.a) {
						polarDrive(1, Math.PI / 4.4);
						sleep(2020);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
						polarDrive(1, -(Math.PI - (Math.PI / 4.4)));
						sleep(1450);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

					} else {
						polarDrive(1, Math.PI / 2.7);
						sleep(1120);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
						polarDrive(1, -(Math.PI - (Math.PI / 2.7)));
						sleep(800);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
					}
					rotateToAngle(180);
					moveLeftRight(0.8);
					sleep(100);
					moveUpDown(1);
					sleep(800);
					while((!(robot.wallDistance.getDistance(DistanceUnit.MM)<2)&&!(robot.wallDistance.getDistance(DistanceUnit.MM)>100)))
						moveUpDown(0.7);
					moveLeftRight(-1);
					markerBlue=robot.markerColor.blue();
					markerRed=robot.markerColor.red();
					sleep(900);
					while(robot.markerColor.blue()<markerBlue+5&&robot.markerColor.red()<markerRed+5)
						moveLeftRight(-0.2);
					rotate(-1);
					sleep(350);
					stopMotors();
					robot.marker1.setPosition(0.3);
					sleep(500);
					robot.marker1.setPosition(1);
					rotateToAngle(270);
					moveUpDown(1);
					sleep(1850);
					stopMotors();
					robot.arm.setPower(1);
					sleep(1500);
					robot.arm.setPower(0);
					robot.extension.setPower(0.5);
					sleep(250);
					robot.extension.setPower(0);
					while (opModeIsActive()) {
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
					}
				}

				if (runtime.seconds() > 120) {
					lightCode = 97;
				} else if (runtime.seconds() > 115) {
					lightCode = 45;
				} else if (runtime.seconds() > 105) {
					if (Math.round(runtime.seconds() * 4) % 2 == 0)
						lightCode = 96;
					else
						lightCode = 100;
				}
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(lightCode - 1));
				//Move Robot
				moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
				moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
				rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*
			PUT FINAL CONTROL SCHEME HERE


			 */
				if ((Math.abs(moveY) > 0.25)) {
					moveUpDown(-moveY);
					lightCode = 57;

				} else if (Math.abs(moveX) > 0.25) {
					moveLeftRight(-moveX);
					lightCode = 67;
				} else if (Math.abs(rotate) > 0.25) {
					rotate(-rotate);
				}
			/*if(!allDirDrive && relativeDrive == 10000) {
				if ((Math.abs(moveY) > 0.25)) {
					moveUpDown(-moveY);
					position = 57;

				} else if (Math.abs(moveX) > 0.25) {
					moveLeftRight(-moveX);
					position = 67;
				}
			}
			else if(allDirDrive){
				if(Math.abs(moveX)>0.25 || Math.abs(moveY)>0.25) {
					if(moveX == 0){
						moveUpDown(moveY);
					}
					else if(moveY == 0){
						moveLeftRight(moveX);
					}
					else {
						polarDrive(Math.abs(Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2))), Math.atan(moveY / moveX));
					}
				}
			}
			else if(relativeDrive != 10000){
				int angleFacing = getAngle();
				int angleWanted = 0;
				double powerControl = 0;
				if(Math.abs(moveX)>0.25){
					powerControl = Math.abs(moveX);
					if(moveX>0){
						angleWanted = 0;
					}
					else{
						angleWanted = 180;
					}
				}
				else if(Math.abs(moveY)>0.25){
					powerControl = Math.abs(moveY);
					if(moveY>0){
						angleWanted = 270;
					}
					else{
						angleWanted = 90;
					}
				}
				polarDrive(powerControl, Math.toRadians((360+angleWanted)-angleFacing));
			}*/
				else if (gamepad1.left_bumper) {
					rotate(-0.2);
					lightCode = 39;
				} else if (gamepad1.right_bumper) {
					rotate(0.2);
					lightCode = 39;
				} else if (gamepad1.dpad_up&&!gamepad1.a) {
					lightCode = 64;
					moveUpDown(-0.3);
				} else if (gamepad1.dpad_down&&!gamepad1.a) {
					lightCode = 64;
					moveUpDown(+0.3);
				} else if (gamepad1.dpad_left&&!gamepad1.a) {
					lightCode = 54;
					moveLeftRight(-0.3);
				} else if (gamepad1.dpad_right&&!gamepad1.a) {
					lightCode = 54;
					moveLeftRight(0.3);
				} else {
					lightCode = 72;
					stopMotors();
				}

				//Move team marker mechanism
				if (gamepad1.left_trigger > 0.1) {
					robot.marker1.setPosition(1);
				}
				if (gamepad1.right_trigger > 0.1) {
					robot.marker1.setPosition(0.3);
				} else {
					//robot.marker.setPower(0.6);
				}

				//lift robot
				if (gamepad2.left_bumper) {
					lightCode = 97;
					robot.lid.setPosition(0.9);

				} else if (gamepad2.right_bumper) {
					robot.lid.setPosition(0.55);
					if (runtime.seconds() < 110) {
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
						sleep(100);
						robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
					}
				}

				if (gamepad2.dpad_up) {
					lift("up");
					lightCode = 81;
				} else if (gamepad2.dpad_down) {
					lift("down");
					lightCode = 81;
				} /*else if (gamepad1.y) {
					lift("up");
					position = 81;
				} else if (gamepad1.a && !gamepad1.start) {
					lift("down");
					position = 81;
				} */else {
					lift("stop");
				}


				if (gamepad2.x) {
					//extends
					extensionPosition = startingPosition;
					robot.extension.setPower(0.5);
					//	while (opModeIsActive() && gamepad1.x) ;
				} else if (gamepad2.b && !gamepad2.start) {
					//retracts
					robot.extension.setPower(-0.5);
					//	while (opModeIsActive() && gamepad1.b) ;
				} else if (gamepad2.y) {
					extend();
				} else if (gamepad2.a && !gamepad2.start) {

					retract();
				} else {
					robot.extension.setPower(0);
				}

				if (Math.abs(gamepad2.left_stick_y) > 0.1) {
					robot.arm.setPower(gamepad2.left_stick_y / 1.5);
					lightCode = 22;
				} else if (gamepad2.left_trigger > 0.1) {
					robot.arm.setPower(gamepad2.left_trigger / 3.25);
					lightCode = 22;
				} else if (gamepad2.right_trigger > 0.1) {
					robot.arm.setPower(-gamepad2.right_trigger / 3.25);
					lightCode = 22;
				} else
					robot.arm.setPower(0);

				telemetry.addData("Red", robot.markerColor.red());
				telemetry.addData("Green", robot.markerColor.green());
				telemetry.addData("Blue", robot.markerColor.blue());
				telemetry.addData("Alpha", robot.markerColor.alpha());

				telemetry.addData("Distance", robot.markerDistance.getDistance(DistanceUnit.INCH));

				telemetry.addData("wallRed", robot.wallColor.red());
				telemetry.addData("wallGreen", robot.wallColor.green());
				telemetry.addData("wallBlue", robot.wallColor.blue());
				telemetry.addData("wallAlpha", robot.wallColor.alpha());

				telemetry.addData("wallDistance", robot.wallDistance.getDistance(DistanceUnit.MM));
				telemetry.addData("wallDistance", !(robot.wallDistance.getDistance(DistanceUnit.MM)<2));
				telemetry.addData("wallDistance", !(robot.wallDistance.getDistance(DistanceUnit.MM)>100));
				telemetry.update();
				//Controller 1 presses back, start, and both bumpers
			/*if(gamepad1.back && gamepad1.start && gamepad1.left_bumper && gamepad1.right_bumper){
				if(!allDirDrive) {
					allDirDrive = true;
				}
				else allDirDrive = false;
			}

			if(gamepad1.back && gamepad1.start && gamepad1.left_trigger>0.5 && gamepad1.right_trigger>0.5){
				if(relativeDrive == 10000) {
					if (gamepad1.a) {
						relativeDrive = 270;
					} else if (gamepad1.b) {
						relativeDrive = 0;
					} else if (gamepad1.x) {
						relativeDrive = 180;
					} else if (gamepad1.y) {
						relativeDrive = 90;
					}
					robot.startingAngle = relativeDrive;
					calibrate();
				}
				else{
					relativeDrive = 10000;
				}
			}*/
			}


		}
	}

}
