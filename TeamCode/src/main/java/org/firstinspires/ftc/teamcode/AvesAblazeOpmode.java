package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.android.dex.util.ExceptionWithContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;
import static org.firstinspires.ftc.teamcode.TF_FIX_AVESABLAZEHARDWARE.CAMERA_CHOICE;

import com.firebase.client.Firebase;
import com.qualcomm.robotcore.robot.Robot;

import org.athenian.ftc.ListenerAction;
import org.athenian.ftc.RobotValues;
import org.athenian.ftc.ValueListener;
import org.athenian.ftc.ValueSource;
import org.athenian.ftc.ValueWriter;

/**
 * Created by Rohan Mathur on 11/9/18.
 */
//My name is Mada Greblep and I approve this message
public abstract class AvesAblazeOpmode extends LinearOpMode implements AvesAblazeOpmodeSimplified {
	String goldMineralLabel="";
			String silverMineralLabel="";
	List<Recognition> updatedRecognitions=null;
	String position="none";
	//	RobotValues fireVals;

	static final String VUFORIA_KEY = "ASre9vb/////AAABmS9qcsdgiEiVmAClC8R25wUqiedmCZI33tlr4q8OswrB3Kg7FKhhuQsUv3Ams+kaXnsjj4VxJlgsopgZOhophhcKyw6VmXIFChkIzZmaqF/PcsDLExsXycCjm/Z/LWQEdcmuNKbSEgc1sTAwKyLvWn6TK+ne1fzboxjtTmkVqu/lBopmR3qI+dtd3mjYIBiLks9WW6tW9zS4aau7fJCNYaU1NPgXfvq1CRjhWxbX+KWSTUtYuFSFUBw2zI5PzIPHaxKrIwDKewo1bOZBUwbqzmm5h0d4skXo3OC0r+1AYrMG0HJrGRpkN9U6umTlYd5oWCqvgBSVxKkOGM1PhNY5cX+sqHpbILgP+QVOFblKSV9i";
	VuforiaLocalizer vuforia;// Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
	TFObjectDetector tfod;
	public void extend(){
		while(Math.abs(robot.extension.getCurrentPosition()-robot.startingExtension)<1600)
			robot.extension.setPower(0.5);
	}
	public void retract(){
		while(Math.abs(robot.extension.getCurrentPosition()-robot.startingExtension)<100)
			robot.extension.setPower(0.5);
	}
	public void deploy(){
		lift();
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
		stopMotors();
	}

	//Stop motors
	public  void stopMotors(){
		robot.motor0.setPower(0);
		robot.motor1.setPower(0);
		robot.motor2.setPower(0);
		robot.motor3.setPower(0);
		robot.arm.setPower(0);
	}
	//rotates from power
	public void rotate(double power){
		robot.motor0.setPower(-power);
		robot.motor1.setPower(-power);
		robot.motor2.setPower(-power);
		robot.motor3.setPower(-power);
	}
	//Moves left-right based off power
	public void moveLeftRight(double power){
		robot.motor0.setPower(power);
		robot.motor1.setPower(power);
		robot.motor2.setPower(-power);
		robot.motor3.setPower(-power);
	}
	//Moves forward-back based off power
	public void moveUpDown(double power){
		robot.motor0.setPower(-power);
		robot.motor1.setPower(power);
		robot.motor2.setPower(-power);
		robot.motor3.setPower(power);
	}
	//Rotate based off power and tics
	public void rotate(double power, int tics){
		if(tics < 0) power = power*-1;
		int initPos = robot.motor0.getCurrentPosition();
		int currPos = initPos;
		while(currPos != initPos+tics&&opModeIsActive()){
			robot.motor0.setPower(-power);
			robot.motor1.setPower(-power);
			robot.motor2.setPower(-power);
			robot.motor3.setPower(-power);
			currPos = robot.motor0.getCurrentPosition();
		}
		stopMotors();
	}
	//Moves left-right based off power(magnitude) and tics
	public void moveLeftRight(double power, int tics){
		int angle = getAngle();
		if(tics<0) power = power*-1;
		int initPos = robot.motor0.getCurrentPosition();
		int currPos = initPos;
		while(Math.abs(currPos) < initPos+tics&&opModeIsActive()){
			robot.motor0.setPower(-power);
			robot.motor1.setPower(-power);
			robot.motor2.setPower(power);
			robot.motor3.setPower(power);
			currPos = robot.motor0.getCurrentPosition();
		}
		stopMotors();
		rotateToAngle(angle);
	}
	public void moveLeftRight(double power, Callable<Boolean> condition){
		double correction=0;
		int angle=getAngle();
		try {
			while (condition.call()&&opModeIsActive()) {
				robot.motor0.setPower(-power-correction);
				robot.motor1.setPower(-power+correction);
				robot.motor2.setPower(power-correction);
				robot.motor3.setPower(power+correction);
				if(Math.abs(getAngle()-angle)>10) {
					rotateToAngle(angle);
				}
			}
		}
		catch(Exception e){

		}
		stopMotors();

	}
	//Moves forward-back based off power(magnitude) and tics
	public void moveUpDown(double power, int tics){
		int angle=getAngle();
		if(tics<0) power = power*-1;
		int initPos = (Math.abs(robot.motor0.getCurrentPosition())+Math.abs(robot.motor1.getCurrentPosition())+Math.abs(robot.motor2.getCurrentPosition())+Math.abs(robot.motor3.getCurrentPosition()))/4;
		int currPos = initPos;
		while(Math.abs(currPos) < initPos+tics&&opModeIsActive()){
			robot.motor0.setPower(power);
			robot.motor1.setPower(-power);
			robot.motor2.setPower(power);
			robot.motor3.setPower(-power);
			currPos = (Math.abs(robot.motor0.getCurrentPosition())+Math.abs(robot.motor1.getCurrentPosition())+Math.abs(robot.motor2.getCurrentPosition())+Math.abs(robot.motor3.getCurrentPosition()))/4;
			if(Math.abs(getAngle()-angle)>360) {
				rotateToAngle(angle);
			}
		}
		stopMotors();
	}

	public void polarDrive(double power, double angle){
		angle=angle-(Math.PI/4);
		robot.motor0.setPower(power*-Math.cos(angle));
		robot.motor1.setPower(power*Math.sin(angle));
		robot.motor3.setPower(power*Math.cos(angle));
		robot.motor2.setPower(power*-Math.sin(angle));
	}

	public void drive(double inches, boolean forward, double power){
		double val = 85;
		if(power>0.5) val = 65;
		if(forward)
			moveUpDown(-power,(int)Math.round(val*inches));
		else{
			moveUpDown(power,(int)Math.round(Math.abs(val*inches)));
		}

	}

	//Returns encoder value for motor based on motor number
	public int getMotorVal(int motorNum){
		switch (motorNum){
			case 0: return robot.motor0.getCurrentPosition();
			case 1: return robot.motor1.getCurrentPosition();
			case 2: return robot.motor2.getCurrentPosition();
			case 3: return robot.motor3.getCurrentPosition();
			default: return 0;
		}
	}
	public boolean resetCoordinates(){
		updatedRecognitions = tfod.getUpdatedRecognitions();
		robot.targetsRoverRuckus.activate();
		robot.targetVisible=false;

		// check all the trackable target to see which one (if any) is visible.
		for (VuforiaTrackable trackable : robot.allTrackables) {
			if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
				robot.targetVisible = true;
				robot.currentTrackable = trackable;
				// getUpdatedRobotLocation() will return null if no new information is available since
				// the last time that call was made, or if the trackable is not currently visible.
				OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
				if (robotLocationTransform != null) {
					robot.lastLocation = robotLocationTransform;

				}
				robot.translation = robot.lastLocation.getTranslation();
				robot.rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
				return robot.targetVisible;
			}
		}
		return robot.targetVisible;
	}
	public void calibrate() {
		BNO055IMU.Parameters imuParameters;
		imuParameters = new BNO055IMU.Parameters();
		imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
		imuParameters.loggingEnabled = true;
		imuParameters.loggingTag = "IMU";
		imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
		robot.imu1.initialize(imuParameters);
		robot.imu.initialize(imuParameters);
	}
	//Returns x coordinate from vuforia
	public int getX(){
		if(!resetCoordinates()) return 10000;
		return Math.round(robot.translation.get(0)/ AvesAblazeHardware.mmPerInch);
	}
	public double getExactX(){
		if(!resetCoordinates()) return 10000;
		return (double)robot.translation.get(0) / AvesAblazeHardware.mmPerInch;

	}
	//Returns y coordinate from vuforia
	public int getY(){
		if(!resetCoordinates()) return 10000;
		return Math.round(robot.translation.get(1)/ AvesAblazeHardware.mmPerInch);
	}
	public double getExactY(){
		if(!resetCoordinates()) return 10000;
		return (double)robot.translation.get(1) / AvesAblazeHardware.mmPerInch;

	}
	//Returns angle based off vuforia or rev hub imu
	public int getAngle(){
		if(!resetCoordinates()&&robot.imu1.isGyroCalibrated()){
			robot.angles=robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			double currentAngle=robot.angles.firstAngle;
			int finalAngle= robot.startingAngle+(int)Math.round(currentAngle);
			if(finalAngle<0){
				return 360+finalAngle;
			}
			return finalAngle;

		}
		else if(!resetCoordinates()&&robot.imu.isGyroCalibrated()){
			robot.angles=robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.YZX, AngleUnit.DEGREES);
			double currentAngle=robot.angles.firstAngle;
			int finalAngle= robot.startingAngle+(int)Math.round(currentAngle);
			if(finalAngle<0){
				return 360+finalAngle;
			}
			return finalAngle;

		}
		else if(resetCoordinates()){
			double oldAngle = robot.rotation.thirdAngle;
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

	public double getExactAngle(){
		if(!resetCoordinates()&&robot.imu1.isGyroCalibrated()){
			robot.angles=robot.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			double currentAngle=robot.angles.firstAngle;
			double finalAngle= (double)robot.startingAngle+currentAngle;
			if(finalAngle<0){
				return 360+finalAngle;
			}
			return finalAngle;

		}
		else if(resetCoordinates()){
			double oldAngle = robot.rotation.thirdAngle;
			double posAngle = oldAngle;
			double finalAngle;
			if (oldAngle < 0) posAngle = 360 - Math.abs(oldAngle);
			if((int) (Math.round(posAngle)) - 45 < 0){
				finalAngle = 360-posAngle;
			}
			else{
				finalAngle = posAngle - 45;
			}
			return finalAngle;
		}
		else{
			return 10000;
		}
	}
	//Runs lift motors
	public void lift(String direction){
		if(direction.equals("up")){
			robot.lift1.setPower(1);
			robot.lift2.setPower(1);
		}
		else if(direction.equals("down")){
			robot.lift1.setPower(-1);
			robot.lift2.setPower(-1);
		}
		else if(direction.equals("stop")){
			robot.lift1.setPower(0);
			robot.lift2.setPower(0);
		}
	}
	//Rotates to certain angle
	public void rotateToAngle(int newAngle) {
		int diff = newAngle - getAngle();
		int diff1=Math.abs(diff-360);
		if (newAngle == getAngle()) {
			stopMotors();
			return;
		}
		while (Math.abs(getAngle() - newAngle) > 1&&opModeIsActive()) {
			telemetry.addData("newangle", newAngle);
			telemetry.addData("getangle()", getAngle());
			telemetry.update();
			diff = newAngle - getAngle();
			diff1=Math.abs(getAngle() - newAngle);
			if ((diff > 0 && diff < 180) || (diff < 0 && Math.abs(diff) > 180)) {

				if(Math.abs(diff1)<13)
					rotate(-0.06);
				else if(Math.abs(diff1)<50)
					rotate(-0.1);
				else if(Math.abs(diff1)<160)
					rotate(-0.5);
				else
					rotate(-(0.00928571*Math.abs(diff1))+0.128571);
			} else {

				if(Math.abs(diff1)<13)
					rotate(0.06);
				else if(Math.abs(diff1)<50)
					rotate(0.1);
				else if(Math.abs(diff1)<160)
					rotate(0.5);
				else
					rotate((0.00928571*Math.abs(diff1))+0.128571);
			}

		}
		stopMotors();

	}
	public void rotateToAngle(double newAngle) {
		double diff = newAngle - getAngle();
		double diff1=Math.abs(diff-360);
		if (newAngle == getAngle()) {
			stopMotors();
			return;
		}
		while (Math.abs(getAngle() - newAngle) > 1&&opModeIsActive()) {
			if ((diff > 0 && diff < 180) || (diff < 0 && Math.abs(diff) > 180)) {
				rotate((Math.pow(0.0000251029*diff1,2)-0.000462963*diff1+0.07));
				/*if (Math.abs(getAngle() - newAngle) > 60) {
					rotate(-0.5);
				}
				else if (Math.abs(getAngle() - newAngle) > 20) {
					rotate(-0.17);
				} else  {
					rotate(-0.07);
				}*/
			} else {
				rotate(-(Math.pow(0.0000251029*diff1,2)-0.000462963*diff1+0.07));
				/*if (Math.abs(getAngle() - newAngle) > 60) {
					rotate(0.4);
				}
				else if (Math.abs(getAngle() - newAngle) > 20) {
					rotate(0.17);
				} else {
					rotate(0.1);
				}*/
			}

		}
	}
	//Moves to vuforia coordinate from either vuforia or rev imu angle
	public void moveToCoord(int x, int y, int angle, double power) throws IOException{
		resetCoordinates();
		double moveAngle;
		double thetaField;
		double referenceTheta;
		//(-62,-3)
		referenceTheta = Math.atan2(y-getY(),getX() - x);
		thetaField = referenceTheta;
		//calculate what angle in reference to the robot that the robot should move
		moveAngle=thetaField+(Math.PI/2)-Math.toRadians(getAngle());
		if(Math.abs(moveAngle)>Math.PI&&moveAngle<0){
			moveAngle=moveAngle+(2*Math.PI);
		}
		if(Math.abs(moveAngle)>Math.PI&&moveAngle>0){
			moveAngle=moveAngle-(2*Math.PI);
		}
		polarDrive(power,moveAngle);
		while((Math.abs(getX()-x)>2 || Math.abs(getY()-y)>2)&&opModeIsActive()&&resetCoordinates()){
			resetCoordinates();
			if(gamepad1.a)

				stopMotors();
			else{
				polarDrive(power,moveAngle);
			}
			telemetry.addData("(x,y)","("+getX()+","+getY()+")");
			telemetry.addData("(new x,new y","("+x+","+y+")" );
			telemetry.addData("getX() > x",getX() > x);
			telemetry.addData("y < getY()",y < getY());
			telemetry.addData("reference angle", Math.toDegrees(referenceTheta));
			telemetry.addData("goal Angle", Math.toDegrees(moveAngle));
			telemetry.addData("current angle",getAngle());
			telemetry.addData("theta field",Math.toDegrees(thetaField));
			telemetry.addData("condition", Math.abs(getX()-x)>1 || Math.abs(getY()-y)>1);
			telemetry.update();
		}
		if(!resetCoordinates())
			throw new IOException("Vuforia not found");
		stopMotors();
		rotateToAngle(angle);
		stopMotors();

	}

	//More lift motors correctly
	public void lift(){
		while(Math.abs(robot.lift1.getCurrentPosition()-robot.startingHeight)<4600&&opModeIsActive()){
			lift("up");
		}
		lift("stop");
	}
	public void lower(){
		while(Math.abs(robot.lift1.getCurrentPosition()-robot.startingHeight)>1500&&opModeIsActive()){
			lift("down");
		}
		lift("stop");
	}
	public void liftAcc(int tics){
		int initPos = robot.lift1.getCurrentPosition();
		int currPos = initPos;
		while(currPos <= initPos+tics&&opModeIsActive()){
			robot.lift1.setPower(0.5);
			robot.lift2.setPower(0.5);
		}
		robot.lift1.setPower(0);
		robot.lift2.setPower(0);
	}

	//Resets encoders
	public void resetEncodes(){
		robot.motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		robot.motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		robot.motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		robot.motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}
	//Really self explanatory
	public int getLiftHeight(){
		return robot.lift1.getCurrentPosition();
	}
	//Again, super self explanatory
	public void check2Minerals(){
		if (tfod != null) {
			// getUpdatedRecognitions() will return null if no new information is available since
			// the last time that call was made.
			List<Recognition> updatedRecognitions = tfod.getRecognitions();

			if (updatedRecognitions != null) {

				float goldMineral;
				float silverMineral;

				if(updatedRecognitions.size()==3){
					checkMinerals();
				}
				else if (updatedRecognitions.size() >= 2) {
					if(updatedRecognitions.get(0).getLabel().equals(LABEL_GOLD_MINERAL)) {
						goldMineral = updatedRecognitions.get(0).getLeft();
						silverMineral = updatedRecognitions.get(1).getLeft();
					}
					else if(updatedRecognitions.get(1).getLabel().equals(LABEL_GOLD_MINERAL)){
						goldMineral = updatedRecognitions.get(1).getLeft();
						silverMineral = updatedRecognitions.get(0).getLeft();
					}
					else{
						position="right";
						return;
					}
					telemetry.clearAll();
					telemetry.addData("minerals", updatedRecognitions.size());
					telemetry.addData("goldMineral", goldMineral);
					telemetry.addData("silverMineral", silverMineral);
					telemetry.addData("position", position);
					telemetry.update();

					/*
						THESE MAY
						NEED TO BE
						CHANGED
					*/
					if(goldMineral<528){
						position="center";
					}
					else if(goldMineral>528){
						position="left";
					}
					else{
						position="rotate";
					}

				}
			}
		}
	}

	public void checkMinerals(){
		if (tfod != null) {
			// getUpdatedRecognitions() will return null if no new information is available since
			// the last time that call was made.
			List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
			if (updatedRecognitions != null) {
				telemetry.addData("minerals", updatedRecognitions.size());
				telemetry.update();

				if (updatedRecognitions.size() == 3) {
					int goldMineralX = -1;
					int silverMineral1X = -1;
					int silverMineral2X = -1;
					for (Recognition recognition : updatedRecognitions) {
						if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
							goldMineralX = (int) recognition.getLeft();
						} else if (silverMineral1X == -1) {
							silverMineral1X = (int) recognition.getLeft();
						} else {
							silverMineral2X = (int) recognition.getLeft();
						}
					}
					if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
						if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
							position="right";
						} else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
							position="left";
						} else {
							position="center";
						}
					}
				}

			}
		}
	}
/*	public void doFirebase(){
		final Firebase fb = new Firebase("http://localhost");
		fireVals = new RobotValues(fb, 0.5);
		fireVals.add(new ValueWriter("angleVal", new ValueSource() {
			@Override
			public Object getValue() {
				return getAngle();
			}
		}));
		fireVals.start();
	}*/

	public void rememberAngle(){

	}
	public void initVuforia(){
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
		goldMineralLabel=LABEL_GOLD_MINERAL;
		silverMineralLabel=LABEL_SILVER_MINERAL;
	}


}
