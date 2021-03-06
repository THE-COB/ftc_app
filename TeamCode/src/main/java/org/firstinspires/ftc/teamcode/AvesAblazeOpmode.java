package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
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
import java.util.concurrent.TimeUnit;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;

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
	static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
	static final String LABEL_GOLD_MINERAL = "Gold Mineral";
	static final String LABEL_SILVER_MINERAL = "Silver Mineral";

	double markerRed=0;
	double markerBlue=0;
	double wallAlpha=0;

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
		robot.motor0.setPower(-power);
		robot.motor1.setPower(-power);
		robot.motor2.setPower(power);
		robot.motor3.setPower(power);
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
		//updatedRecognitions = tfod.getUpdatedRecognitions();
		/*robot.targetsRoverRuckus.activate();
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
		return robot.targetVisible;*/
		return false;
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
		markerBlue=robot.markerColor.blue();
		markerRed=robot.markerColor.red();

	}

	String format(OpenGLMatrix transformationMatrix) {
		return transformationMatrix.formatAsTransform();
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
		while (Math.abs(getAngle() - newAngle) > 3&&opModeIsActive()) {
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
		while(Math.abs(robot.lift1.getCurrentPosition()-robot.startingHeight)<3900&&opModeIsActive()){
			robot.lift1.setPower(1);
			robot.lift2.setPower(1);
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
		/*if (tfod != null) {
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


					if(goldMineral<528){
						position="center";
					}
					else if(goldMineral>528){
						position="left";
					}
					else{
						position="rotate";
					}*/

		//		}
		//	}
		//}
	}

	public void checkMinerals(){
		/*if (tfod != null) {
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
		}*/
	}

	public void rememberAngle(){

	}
	/**
	 * Initialize the Vuforia localization engine.
	 */
	public void initVuforia() {
		/*
		 * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
		 */
		VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

		parameters.vuforiaLicenseKey = VUFORIA_KEY;
		parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

		//  Instantiate the Vuforia engine
		vuforia = ClassFactory.getInstance().createVuforia(parameters);

		// Loading trackables is not necessary for the Tensor Flow Object Detection engine.
	}

	/**
	 * Initialize the Tensor Flow Object Detection engine.
	 */
	public void initTfod() {
		int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
				"tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters((tfodMonitorViewId));
		tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
		tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
	}

	public void finalMinFinder(){
		if (opModeIsActive()) {
			/*initVuforia();


			if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
				initTfod();
			} else {
				telemetry.addData("Sorry!", "This device is not compatible with TFOD");
			}*/

			if (opModeIsActive()) {
				/** Activate Tensor Flow Object Detection. */
				if (tfod != null) {
					tfod.activate();
				}

					if (tfod != null) {
						// getUpdatedRecognitions() will return null if no new information is available since
						// the last time that call was made.
						List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
						if (updatedRecognitions != null) {
							Recognition rec0;
							Recognition rec1;
							position = "none";
							if (updatedRecognitions.size() > 1) {
								if (updatedRecognitions.size() > 2) {
									double conf = -1;
									int recNum = -1;
									for (int i = 0; i < updatedRecognitions.size(); i++) {
										if (updatedRecognitions.get(i).getConfidence() > conf) {
											recNum = i;
											conf = updatedRecognitions.get(i).getConfidence();
										}
									}
									rec0 = updatedRecognitions.get(recNum);
									updatedRecognitions.remove(recNum);
									conf = -1;
									recNum = -1;
									for (int i = 0; i < updatedRecognitions.size(); i++) {
										if (updatedRecognitions.get(i).getConfidence() > conf) {
											recNum = i;
											conf = updatedRecognitions.get(i).getConfidence();
										}
									}
									rec1 = updatedRecognitions.get(recNum);
								} else {
									rec0 = updatedRecognitions.get(0);
									rec1 = updatedRecognitions.get(1);
								}

								if (rec0.getLabel().equals(LABEL_GOLD_MINERAL)) {
									telemetry.addData("rec0", rec0.getLeft());
									telemetry.addData("rec1", rec1.getLeft());
									telemetry.addData("rec0", "gold mineral");
									telemetry.update();
									if (rec0.getTop() > rec1.getTop()) {
										position = "left";
									} else {
										position = "center";
									}
								} else if (rec1.getLabel().equals(LABEL_GOLD_MINERAL)) {
									telemetry.addData("rec0", rec0.getLeft());
									telemetry.addData("rec1", rec1.getLeft());
									telemetry.addData("rec1", "gold mineral");
									telemetry.update();
									if (rec1.getTop() > rec0.getTop()) {
										position = "left";
									} else {
										position = "center";
									}
								} else {
									position = "right";
								}
							}
							telemetry.addData("Gold Mineral Position", position);

							telemetry.update();
							ElapsedTime scanTime = new ElapsedTime();
							if (updatedRecognitions.size() == 1) {
								if (Math.round(scanTime.milliseconds() / 200) % 10 == 0) {
									robot.phoneServoX.setPosition(robot.phoneServoX.getPosition() - 0.00055);
									robot.phoneServoY.setPosition(robot.phoneServoY.getPosition() + 0.00035);
								}
							}
							stopMotors();
						}
					}

				}
		}
	}

	public void move(double x, double y){
		double vx=0;
		double vy=0;
		double dx=0;
		double dy=0;
		Acceleration a;

		ElapsedTime t=new ElapsedTime();
		while(!gamepad1.a&&opModeIsActive()){
			polarDrive(1,Math.atan(y/x));
			t.reset();
			a=robot.imu.getLinearAcceleration();

			//integrate the acceleration vector to get velocity
			vx+=(a.xAccel-0.1)*t.seconds();
			vy+=a.yAccel*t.seconds();

			//integrate the velocity vector to get displacement
			dx+=vx*t.seconds();
			dy+=vy*t.seconds();
			telemetry.addData("t", t);
			telemetry.addData("a", a.toString());
			telemetry.addData("v", vx+", "+ vy);
			telemetry.addData("d", dx+", "+dy);
			telemetry.update();
					while(gamepad1.b&&opModeIsActive()){
						stopMotors();
					}
		}
		stopMotors();
	}
	public void sample(){
		ElapsedTime scanTime=new ElapsedTime();
		while(scanTime.seconds()<2.3&&position.equals("none")&&opModeIsActive()){
			if(tfod.getUpdatedRecognitions()!=null&&tfod.getRecognitions().size()>0)
				if(tfod.getRecognitions().get(0).getLabel().equals(LABEL_GOLD_MINERAL)&&tfod.getRecognitions().get(0).getHeight()>65){
					position="left";
				}
		}
		while(scanTime.seconds()<4.85&&position.equals("none")&&opModeIsActive()){
			robot.phoneServoX.setPosition(1);
			robot.phoneServoY.setPosition(0.67);
			if(tfod.getUpdatedRecognitions()!=null&&tfod.getRecognitions().size()>0){
				if(tfod.getRecognitions().get(0).getLabel().equals(LABEL_GOLD_MINERAL)&&scanTime.seconds()>1.5&&tfod.getRecognitions().get(0).getHeight()>65){
					position="center";
					telemetry.addData("mPosition", position);
					telemetry.update();
				}
			}
		}
		if(position.equals("none"))
			position="right";
		if (position.equals("left") || gamepad1.b) {
			polarDrive(1, 2 * Math.PI / 2.97);
			sleep(1420);
			markerBlue=robot.markerColor.blue();
			markerRed=robot.markerColor.red();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			ElapsedTime driveTime=new ElapsedTime();
			while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15&&opModeIsActive()&&driveTime.seconds()<1.4)
			polarDrive(1, -(Math.PI - (2 * Math.PI / 2.9)));
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

		} else if (position.equals("right") || gamepad1.a) {
			polarDrive(1, Math.PI / 4.4);
			sleep(2230);
			markerBlue=robot.markerColor.blue();
			markerRed=robot.markerColor.red();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));
			ElapsedTime driveTime=new ElapsedTime();
			while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15&&opModeIsActive()&&driveTime.seconds()<2.25)
				polarDrive(1, -(Math.PI - (Math.PI / 4.4)));
			polarDrive(1, -Math.PI+0.3);
			sleep(100);
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));

		} else {
			telemetry.addData("it ran center","it certainly ran center");
			telemetry.update();
			polarDrive(1, Math.PI / 2.7);

			sleep(1200);
			markerBlue=robot.markerColor.blue();
			markerRed=robot.markerColor.red();
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(5-1));

			ElapsedTime driveTime=new ElapsedTime();
			while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15&&opModeIsActive()&&driveTime.seconds()<2)
				polarDrive(1, -(Math.PI - (Math.PI / 2.7)));
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4-1));
		}
		telemetry.addData("Mineral Color", position);
		telemetry.update();

	}
	public boolean isAlive(){
		return !isStopRequested()&&opModeIsActive();
	}

	public void safeSleep(double milliseconds){
		ElapsedTime et = new ElapsedTime();

		while(et.time()< et.startTime()+milliseconds);
	}

}
