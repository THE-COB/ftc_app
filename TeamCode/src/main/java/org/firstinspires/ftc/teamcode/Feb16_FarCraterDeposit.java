package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "Feb9DepotFarCraterDeposit", group = "AAA")
public class Feb16_FarCraterDeposit extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	public class initTfod implements Runnable{
		@Override
		public void run() {
			initVuforia();


			if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
				initTfod();
			} else {
				telemetry.addData("Sorry!", "This device is not compatible with TFOD");
			}
			/** Activate Tensor Flow Object Detection. */
			if (tfod != null) {
				tfod.activate();
			}
		}
	}
	@Override
	public void runOpMode() {

		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
		robot.phoneServoX.setPosition(.7);//(0.26);
		robot.phoneServoY.setPosition(.67);//(0.61);
		waitForStart();
		int closedExtension = robot.extension.getCurrentPosition();
		int closedArm = robot.arm.getCurrentPosition();
		new Thread(new initTfod()).start();
		runtime = new ElapsedTime();
if(opModeIsActive()){

	/*rotateToAngle(121);
	ElapsedTime t=new ElapsedTime();
	robot.arm.setPower(1);
	while (opModeIsActive() && Math.abs(robot.extension.getCurrentPosition() - closedExtension) < 2900) {
		telemetry.addData("extension", robot.extension.getCurrentPosition());
		telemetry.addData("closed", closedExtension);
		telemetry.update();
		robot.extension.setPower(1);
		if(t.seconds()>1){
			robot.arm.setPower(0);
		}
	}
	telemetry.addData("extension", robot.extension.getCurrentPosition());
	telemetry.addData("closed", closedExtension);
	telemetry.update();

	robot.extension.setPower(0);

	robot.arm.setPower(0);
	robot.arm.setPower(1);
	sleep(700);
	robot.arm.setPower(-1);
	sleep(300);
	robot.arm.setPower(1);
	sleep(700);
	robot.arm.setPower(-1);
	rotateToAngle(135);
	sleep(500);
	robot.arm.setPower(0);
	robot.lid.setPosition(0.55);*/




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
	robot.startingAngle = 132;
	rotateToAngle(135);
	ElapsedTime scanTime=new ElapsedTime();
	while(scanTime.seconds()<2.25&&position.equals("none")&&opModeIsActive()){
		if(tfod.getUpdatedRecognitions()!=null&&tfod.getRecognitions().size()>0)
			if(tfod.getRecognitions().get(0).getLabel().equals(LABEL_GOLD_MINERAL)&&tfod.getRecognitions().get(0).getHeight()>65){
				position="left";
			}
	}
	while(scanTime.seconds()<5.25&&position.equals("none")&&opModeIsActive()){
		robot.phoneServoX.setPosition(0.61);
		robot.phoneServoY.setPosition(0.67);
		if(tfod.getUpdatedRecognitions()!=null&&tfod.getRecognitions().size()>0)
			if(tfod.getRecognitions().get(0).getLabel().equals(LABEL_GOLD_MINERAL)&&scanTime.seconds()>2.45&&tfod.getRecognitions().get(0).getHeight()>65){
				position="center";
			}
	}
	if(position.equals("none"))
		position="right";
	position="left";
	if(position.equals("left")) {
		rotateToAngle(144);
		ElapsedTime t = new ElapsedTime();
		robot.arm.setPower(1);
		while (opModeIsActive() && Math.abs(robot.extension.getCurrentPosition() - closedExtension) < 2200) {
			telemetry.addData("extension", robot.extension.getCurrentPosition());
			telemetry.addData("closed", closedExtension);
			telemetry.update();
			robot.extension.setPower(1);
			if (t.seconds() > 1.2) {
				robot.arm.setPower(0);
			}
		}
		telemetry.addData("extension", robot.extension.getCurrentPosition());
		telemetry.addData("closed", closedExtension);
		telemetry.update();

		robot.extension.setPower(0);
		robot.arm.setPower(0);
		robot.arm.setPower(1);
		sleep(700);
		robot.arm.setPower(-1);
		sleep(300);
		robot.arm.setPower(1);
		sleep(700);
		robot.arm.setPower(-1);
		moveLeftRight(-1);
		sleep(20);
		rotateToAngle(150);
		moveUpDown(-1);
		sleep(20);

		robot.extension.setPower(1);
		sleep(200);
		robot.extension.setPower(0);

		sleep(190);
		robot.arm.setPower(0);
		robot.lid.setPosition(0.55);
	}
	/*rotate(-1);
	sleep(220);
	rotateToAngle(180);
	moveLeftRight(0.8);
	sleep(100);
	robot.wallServo.setPosition(0.92);
	moveUpDown(1);
	sleep(1050);
	ElapsedTime wallTime=new ElapsedTime();
	while((!(robot.wallDistance.getDistance(DistanceUnit.MM)<2)&&!(robot.wallDistance.getDistance(DistanceUnit.MM)>100))&&wallTime.seconds()<2&&opModeIsActive())
		moveUpDown(0.2);
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
	sleep(15);
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
	stopMotors();*/


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
