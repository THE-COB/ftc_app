package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;
import static org.firstinspires.ftc.teamcode.TF_FIX_AVESABLAZEHARDWARE.CAMERA_CHOICE;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="ROHAN TOUCHED THIS", group="Test")
//Rohan Don't touch this I swear to God
public class TF_FIX_TELEOP0 extends AbstractTFFIX {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	double startingPosition=0;
	int position;
	@Override
	public void runOpMode() {
		try {
			robot.init(hardwareMap);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(60));
		} catch (Exception e){
			tfod.shutdown();
		}
		waitForStart();
		runtime.reset();
		position=57;
		while(opModeIsActive()) {
			telemetry.addData("time",Math.round(runtime.seconds()));
			telemetry.addData("armLength", robot.extension.getCurrentPosition());
			telemetry.addData("liftHeight", robot.lift1.getCurrentPosition());
			telemetry.update();
			if(runtime.seconds()>120){
				position=97;
			}
			else if(runtime.seconds()>115){
				position=45;
			}
			else if(runtime.seconds()>105){
				if(Math.round(runtime.seconds()*4)%2==0)
					position=96;
				else
					position=100;
			}
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(position-1));
			//Move Robot
			moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
			moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
			rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			/*
			PUT FINAL CONTROL SCHEME HERE


			 */

			if ((Math.abs(moveY) > 0.25)) {
				moveUpDown(-moveY);
				position=57;

			}
			else if (Math.abs(moveX) > 0.25) {
				moveLeftRight(-moveX);
				position=67;
			}
			else if (Math.abs(rotate) > 0.25) {
				rotate(-rotate);
				if(Math.round(runtime.seconds()*8)%2==0){
					position=3;
				}
				else{
					position=100;
				}
				/*if(Math.round(runtime.seconds()*6)%6==0)
					position=68;
				else if(Math.round(runtime.seconds()*6+1)%6==0){
					position=58;
				}
				else if(Math.round(runtime.seconds()*6+2)%6==0){
					position=46;
				}
				else if(Math.round(runtime.seconds()*6+3)%6==0){
					position=6;
				}
				else if(Math.round(runtime.seconds()*4)%2==0){
					position=48;
				}
				else{
					position=45;
				}*/
			}
			else if (gamepad1.left_bumper){
				rotate(-0.1);
				position=39;
			}
			else if (gamepad1.right_bumper){
				rotate(0.1);
				position=39;
			}
			else if(gamepad1.dpad_up){
				position=64;
				moveUpDown(-0.3);
			}
			else if(gamepad1.dpad_down){
				position=64;
				moveUpDown(+0.3);
			}
			else if(gamepad1.dpad_left){
				position=54;
				moveLeftRight(-0.3);
			}
			else if(gamepad1.dpad_right){
				position=54;
				moveLeftRight(0.3);
			}
			else{
				position=72;
				stopMotors();
			}

			//Move team marker mechanism
			if(gamepad1.left_trigger>0.1){
				robot.marker1.setPosition(1);
			}
			if(gamepad1.right_trigger>0.1){
				robot.marker1.setPosition(0.3);
			}

			else{
				//robot.marker.setPower(0.6);
			}

			//lift robot
			if(gamepad2.left_bumper){
				position=97;
				robot.lid.setPosition(0.9);

			}
			else if(gamepad2.right_bumper){
				robot.lid.setPosition(0.55);
				if(runtime.seconds()<110) {
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

			if(gamepad2.dpad_up){
				lift("up");
				position=81;
			}
			else if (gamepad2.dpad_down){
				lift("down");
				position=81;
			}
			else if (gamepad1.y){
				lift("up");
				position=81;
			}
			else if (gamepad1.a && !gamepad1.start){
				lift("down");
				position=81;
			}
			else{
				lift("stop");
			}



			if(gamepad2.x){
				//extends
				extensionPosition=startingPosition;
				robot.extension.setPower(0.5);
				//	while (opModeIsActive() && gamepad1.x) ;
			}
			else if(gamepad2.b&&!gamepad2.start) {
				//retracts
				robot.extension.setPower(-0.5);
				//	while (opModeIsActive() && gamepad1.b) ;
			}
			else if(gamepad2.y){
				extend();
			}
			else if(gamepad2.a&&!gamepad2.start){

				retract();
			}
			else{
				robot.extension.setPower(0);
			}

			if(Math.abs(gamepad2.left_stick_y)>0.1) {
				robot.arm.setPower(gamepad2.left_stick_y / 1.5);
				position=22;
			}
			else if(gamepad2.left_trigger>0.1){
				robot.arm.setPower(gamepad2.left_trigger/3.25);
				position=22;
			}
			else if(gamepad2.right_trigger>0.1){
				robot.arm.setPower(-gamepad2.right_trigger/3.25);
				position=22;
			}
			else
				robot.arm.setPower(0);
		}


	}

	private void initVuforia(){
		robot.parameters.vuforiaLicenseKey = VUFORIA_KEY ;
		robot.parameters.cameraDirection   = CAMERA_CHOICE;

		//  Instantiate the Vuforia engine
		vuforia = ClassFactory.getInstance().createVuforia(robot.parameters);

		// Load the data sets that for the trackable objects. These particular data
		// sets are stored in the 'assets' part of our application.
		robot.targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
		VuforiaTrackable blueRover = robot.targetsRoverRuckus.get(0);
		blueRover.setName("Blue-Rover");
		VuforiaTrackable redFootprint = robot.targetsRoverRuckus.get(1);
		redFootprint.setName("Red-Footprint");
		VuforiaTrackable frontCraters = robot.targetsRoverRuckus.get(2);
		frontCraters.setName("Front-Craters");
		VuforiaTrackable backSpace = robot.targetsRoverRuckus.get(3);
		backSpace.setName("Back-Space");

		// For convenience, gather together all the trackable objects in one easily-iterable collection */
		robot.allTrackables = new ArrayList<VuforiaTrackable>();
		robot.allTrackables.addAll(robot.targetsRoverRuckus);

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
				.translation(0, robot.mmFTCFieldWidth, robot.mmTargetHeight)
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
				.translation(0, -robot.mmFTCFieldWidth, robot.mmTargetHeight)
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
				.translation(-robot.mmFTCFieldWidth, 0, robot.mmTargetHeight)
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
				.translation(robot.mmFTCFieldWidth, 0, robot.mmTargetHeight)
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
		for (VuforiaTrackable trackable : robot.allTrackables)
		{
			((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, robot.parameters.cameraDirection);
		}

		initTfod();
	}

	public void initTfod() {
		int tfodMonitorViewId = robot.hwMap.appContext.getResources().getIdentifier(
				"tfodMonitorViewId", "id", robot.hwMap.appContext.getPackageName());
		TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
		tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
		tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
	}

}
