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

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;


public interface opmodeInterfaceTFFIX {
	/**
	 * The robot object required of every OpMode
	 */
	public TF_FIX_AVESABLAZEHARDWARE robot= new TF_FIX_AVESABLAZEHARDWARE();

	/**
	 *Deploys the robot and then centers it onto the field
	 */
	public void deploy();

	/**
	 * Extends the slide on the mineral arm
	 */
	public void extend();

	/**
	 * Retracts the slide on the mineral arm
	 */
	public void retract();

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
	 * Sets the robot to move in any direction based off the angle and magnitude of the gamepad joystick
	 * @param power the power the robot should move at (always positive)
	 * @param angle the angle the robot should move at (-pi,pi)
	 */
	public void polarDrive(double power, double angle);

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

	/**
	 * Runs both lift motors in a direction
	 * "up", "down", and "stop" are the only Strings that change the motor power
	 *
	 * @param direction the String of the direction that you want to lift
	 */
	public void lift(String direction);


	/**
	 * Rotates to an integer angle
	 * @param newAngle the new Angle that the robot should rotate to
	 */
	public void rotateToAngle(int newAngle);

	/**
	 * Rotates to an exact angle
	 * @param newAngle the new Angle that the robot should rotate to
	 */
	public void rotateToAngle(double newAngle);


	/**
	 * Moves to the coordinate and rotates to the angle specified
	 * Throws IOException if Vuforia is not found
	 *
	 * @param x
	 * @param y
	 * @param angle
	 */
	public void moveToCoord(int x, int y, int angle, double power) throws IOException;

	/**
	 * Raises Lift
	 */
	public void lift();

	/**
	 * Lowers lift
	 */
	public void lower();

	/**
	 * @return encoder value of the lift motors
	 */
	public int getLiftHeight();

	/**
	 * Checks to see where the gold mineral is located and updates #position accordingly
	 */
	public void checkMinerals();
}
