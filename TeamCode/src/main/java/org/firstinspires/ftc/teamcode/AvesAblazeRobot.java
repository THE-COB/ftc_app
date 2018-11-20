package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;

/**
 * Created by Rohan Mathur on 9/15/18.
 */
public interface AvesAblazeRobot {
	public AvesAblazeHardware robot= new AvesAblazeHardware();

	/**
	 *Deploys the robot and then centers it onto the field
	 */
	public void deploy();


	/**
	 * Stops all DC Motors on the robot
	 */
	public  void stopMotors();


	/**
	 * Sets the robot to rotate at a certain power
	 *
	 * @param power the power that the motors should be set to rotate
	 */
	public void rotate(double power);

	/**
	 * Sets the robot to strafe in the x direction
	 * @param power the power that the motors should be set to strafe at
	 */
	public void moveLeftRight(double power);

	/**
	 * Sets the robot to move in the y direction
	 *
	 * @param power the power that the motors should be set to move at
	 */
	public void moveUpDown(double power);

	/**
	 * Sets the robot to rotate for a certain amount of encoder ticks
	 *
	 * @param power the power that the motors should be set to rotate at
	 * @param tics the amount of encoder ticks that the robot should rotate
	 */
	public void rotate(double power, int tics);

	/**
	 * Sets the robot to strafe for a certain amount of encoder tics
	 *
	 * @param power the power that the motors should be set to strafe at
	 * @param tics the amount of encoder ticks that the robot should strafe
	 */
	public void moveLeftRight(double power, int tics);

	/**
	 * Sets the robot to move in the y direction for a certain amount of encoder tics
	 *
	 * @param power the power that the motors should be set to move at
	 * @param tics the amount of encoder ticks that the robot should move
	 */
	public void moveUpDown(double power, int tics);

	/**
	 * Sets the robot to strafe while a condition is met
	 *
	 * @param power the power that the robot should strafe at
	 * @param condition the condition that should be true while the robot is strafing
	 */
	public void moveLeftRight(double power, Callable<Boolean> condition);

	/**
	 * Drive in the y direction for a certain amount of inches
	 *
	 * @param inches the distance that the robot should drive for
	 * @param forward is true if the robot should drive forward, false otherwise
	 * @param power the magnitude of the power that the robots should be set at
	 */
	public void drive(double inches, boolean forward, double power);

	/**
	 * Returns the encoder value of a drive motor
	 * Returns 0 if motorNum > 3
	 *
	 * @param motorNum the motor number of the drive motor
	 * @return the encoder value of the given motor number
	 */
	public int getMotorVal(int motorNum);

	/**
	 * Resets the values in the translation and rotation arrays of the AvesAblazeHardware robot
	 *
	 * @return true if vuforia is found, false otherwise
	 */
	public boolean resetCoordinates();

	/**
	 * Calibrates any gyros or Rev imus on the robot
	 * #robot.startingAngle identifies the starting angle
	 */
	public void calibrate();

	/**
	 * Returns the x coordinate of the robot to the nearest integer
	 * Returns 10000 if Vuforia is not found
	 *
	 * @return the x position of the robot
	 */
	public int getX();

	/**
	 * Returns the exact x coordinate of the robot
	 * Returns 10000 if Vuforia is not found
	 *
	 * @return the x position of the robot
	 */
	public double getExactX();

	/**
	 * Returns the y coordinate of the robot to the nearest integer
	 * Returns 10000 if Vuforia is not found
	 *
	 * @return the position of the robot
	 */
	public int getY();

	/**
	 * Returns the exact y coordinate of the robot
	 * Returns 10000 if Vuforia is not found
	 *
	 * @return the y position of the robot
	 */
	public double getExactY();

	/**
	 * Returns the angle of the robot to the nearest integer
	 * Returns 10000 if Vuforia is not found
	 *
	 * @return the angle of the robot
	 */
	public int getAngle();

	/**
	 * Returns the exact angle of the robot
	 * Returns 10000 if Vuforia is not found and gyros not calibrated
	 *
	 * @return the angle of the robot
	 */
	public double getExactAngle();

	//Runs lift motors
	public void lift(String direction);

	//Rotates to certain angle
	public void rotateToAngle(int newAngle);

	public void rotateTo(int newAngle);

	//Moves to vuforia coordinate from either vuforia or rev imu angle
	public void moveToCoord(int x, int y, int angle);

	//More lift motors correctly
	public void lift();

	public void lower();

	public void liftAcc(int tics);

	//Resets encoders
	public void resetEncodes();

	//Really self explanatory
	public int getLiftHeight();

	//Again, super self explanatory
	public void checkMinerals();


}
