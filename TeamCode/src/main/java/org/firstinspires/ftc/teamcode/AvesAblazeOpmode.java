package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 11/9/18.
 */
public abstract class AvesAblazeOpmode extends LinearOpMode {
	public AvesAblazeHardware robot=new AvesAblazeHardware();
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
		lower();
	}
	//Stop motors
	public  void stopMotors(){
		robot.motor0.setPower(0);
		robot.motor1.setPower(0);
		robot.motor2.setPower(0);
		robot.motor3.setPower(0);
		robot.lift1.setPower(0);
		robot.lift2.setPower(0);
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
					rotateTo(angle);
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
				rotateTo(angle);
			}
		}
		stopMotors();
	}

	//Moves forward-back based off inches, if it's moving forward and power(magnitude)
	public void drive(double inches, boolean forward, double power){
		double val = 85;
		if(power>0.5) val = 65;
		if(forward)
			moveUpDown(-power,(int)Math.round(val*inches));
		else{
			moveUpDown(power,(int)Math.round(val*inches));
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
	}
	//Returns x coordinate from vuforia
	public int getX(){
		if(!resetCoordinates()) return 10000;
		return Math.round(robot.translation.get(0)/ AvesAblazeHardware.mmPerInch);
	}
	//Returns y coordinate from vuforia
	public int getY(){
		if(!resetCoordinates()) return 10000;
		return Math.round(robot.translation.get(1)/ AvesAblazeHardware.mmPerInch);
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

	//Runs lift motors
	public void lift(String direction){
		if(direction.equals("up")){
			robot.lift1.setPower(-1);
			robot.lift2.setPower(-1);
		}
		else if(direction.equals("down")){
			robot.lift1.setPower(1);
			robot.lift2.setPower(1);
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
			diff1=Math.abs(getAngle() - newAngle);
			if ((diff > 0 && diff < 180) || (diff < 0 && Math.abs(diff) > 180)) {

				if(Math.abs(diff1)<13)
					rotate(-0.06);
				else if(Math.abs(diff1)<40)
					rotate(-0.1);
				else
					rotate(-(0.00928571*Math.abs(diff1))+0.128571);
				/*if (Math.abs(getAngle() - newAngle) > 60) {
					rotate(-0.5);
				}
				else if (Math.abs(getAngle() - newAngle) > 20) {
					rotate(-0.17);
				} else  {
					rotate(-0.07);
				}*/
			} else {

				if(Math.abs(diff1)<13)
					rotate(0.06);
				else if(Math.abs(diff1)<40)
					rotate(0.1);
				else
					rotate((0.00928571*Math.abs(diff1))+0.128571);
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
		stopMotors();

	}
	public void rotateTo(int newAngle) {
		int diff = newAngle - getAngle();
		int diff1=Math.abs(diff-360);
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
	public void moveToCoord(int x, int y, int angle){
		int oldAngle = getAngle();
		rotateToAngle(angle);
		while(getX()!=x && getY()!=y&&opModeIsActive()){
			moveUpDown(0.25);
		}
		stopMotors();
	}

	//More lift motors correctly
	public void lift(){
		while(robot.lift1.getCurrentPosition()<3350+robot.startingHeight&&opModeIsActive()){
			lift("up");
		}
		lift("stop");
	}
	public void lower(){
		while(robot.lift1.getCurrentPosition()>robot.startingHeight+50&&opModeIsActive()){
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
	public String checkColor() {

		if (robot.sensorColor.blue() / Math.pow(20 - robot.sensorDistance.getDistance(DistanceUnit.INCH), 1) < 1.2) {
			return "yellow";
		} else if (robot.sensorDistance.getDistance(DistanceUnit.INCH) < 20) {
			return "white";
		} else {
			return "nothing";
		}
	}

}