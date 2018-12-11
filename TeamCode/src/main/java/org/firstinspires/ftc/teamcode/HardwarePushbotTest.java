package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxPwmOutputController;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevSPARKMini;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.PWMOutputController;
import com.qualcomm.robotcore.hardware.PWMOutputImpl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.tree.DCTree;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptRevSPARKMini;

import static org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot.MID_SERVO;

/**
 * Created by Rohan Mathur on 9/15/18.
 */
public class HardwarePushbotTest
{
	/* Public OpMode members. */

	HardwareMap hwMap           =  null;
	RevBlinkinLedDriver lights;
	boolean working=true;
	String exception;
	/* Constructor */
	public HardwarePushbotTest(){

	}

	/* Initialize standard Hardware interfaces */
	public void init(HardwareMap ahwMap) {
		// Save reference to Hardware map
		hwMap = ahwMap;
		lights = hwMap.get(RevBlinkinLedDriver.class, "lights");

/*		threader = hwMap.get(CRServo.class, "threader");
		threader.setPower(0);

		door = hwMap.get(Servo.class, "door");
		door.setPosition(0.5);*/

	}
}