package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "DepotFarCraterNoSample5s", group = "AAA")
public class Feb13_FarCraterNoSample5s extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;

	@Override
	public void runOpMode() {

		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		waitForStart();
		runtime = new ElapsedTime();
if(opModeIsActive()){
	ElapsedTime waitTime=new ElapsedTime();
while(waitTime.seconds()<5&&opModeIsActive());
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
	if(position.equals("left")){
		robot.phoneServoX.setPosition(0.31);
		robot.phoneServoY.setPosition(0.5);
		ElapsedTime secondScan=new ElapsedTime();
		while(secondScan.seconds()<1&&opModeIsActive()) {
			try {
				if (tfod.getRecognitions().get(0).getLabel().equals(LABEL_SILVER_MINERAL)) {
					position.equals("right");
					telemetry.addData("status", "positionChanged");
				}
			}
			catch(Exception e){

			}
		}
		}
	rotateToAngle(180);
	moveLeftRight(0.8);
	sleep(100);
	robot.wallServo.setPosition(0.92);
	moveUpDown(1);
	ElapsedTime wallTime=new ElapsedTime();
	while(opModeIsActive()&&wallTime.seconds()<1.2);
	robot.wallServo.setPosition(0.3);
	moveLeftRight(-1);
	markerBlue=robot.markerColor.blue();
	markerRed=robot.markerColor.red();
	sleep(900);
	ElapsedTime depotTime=new ElapsedTime();
	while(robot.markerColor.blue()<markerBlue+15&&robot.markerColor.red()<markerRed+15&&opModeIsActive()&&depotTime.seconds()<2)
		moveLeftRight(-0.2);
	rotate(-1);
	robot.marker1.setPosition(0.3);
	sleep(350);
	stopMotors();

	sleep(500);
	robot.marker1.setPosition(1);
	moveUpDown(1);
	sleep(150);
	rotateToAngle(270);

	moveUpDown(1);
	ElapsedTime drivetime= new ElapsedTime();
	while(opModeIsActive()&&drivetime.seconds()<1.45&&runtime.seconds()<28.5){
	}
	if(drivetime.seconds()>1.6) {
		stopMotors();
	}
	else{
		robot.extension.setPower(0.5);
	}
	robot.arm.setPower(1);
	ElapsedTime armTime=new ElapsedTime();
	while(armTime.seconds()<1.2&&opModeIsActive()){
		if(drivetime.seconds()>1.65){
			stopMotors();
		}
	}

	robot.arm.setPower(0);
	robot.extension.setPower(0);
	stopMotors();


	/*moveUpDown(1);
	sleep(1850);
	stopMotors();
	robot.arm.setPower(1);
	sleep(1500);
	robot.arm.setPower(0);
	robot.extension.setPower(0.5);
	sleep(250);
	robot.extension.setPower(0);*/
	while (opModeIsActive()) {
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		sleep(100);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
		sleep(100);
	}
	}}
}
