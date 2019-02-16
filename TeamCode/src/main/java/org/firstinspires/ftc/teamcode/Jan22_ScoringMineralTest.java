package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

/**
 * Created by Rohan Mathur on 1/22/19.
 */

@Autonomous(name = "DepotScoringMinerals", group = "AAA")
public class Jan22_ScoringMineralTest extends  AvesAblazeOpmode {

	private ElapsedTime runtime;
	float moveY;
	float moveX;
	float rotate;
	double extensionPosition=1;
	int closedExtension;
	int closedArm;

	@Override
	public void runOpMode() {
		runtime = new ElapsedTime();
		robot.init(hardwareMap);
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(4 - 1));
		calibrate();
		robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));

		waitForStart();
		if (opModeIsActive()) {



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

			ElapsedTime scanTime = new ElapsedTime();
			stopMotors();

			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(99 - 1));
			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(95 - 1));
			robot.startingAngle = 135;
			rotateToAngle(135);

			robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(91 - 1));
			closedExtension = robot.extension.getCurrentPosition();
			closedArm = robot.arm.getCurrentPosition();
			position="center";
			if (position.equals("center")) {
				rotateToAngle(123);
ElapsedTime t=new ElapsedTime();
				robot.arm.setPower(1);
				while (opModeIsActive() && Math.abs(robot.extension.getCurrentPosition() - closedExtension) < 3220) {
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
				robot.arm.setPower(-0.5);
				rotateToAngle(135);
				robot.arm.setPower(-0.5);
				while (opModeIsActive() && robot.extension.getCurrentPosition() - closedExtension < 3400) {
					robot.extension.setPower(1);
				}
				robot.extension.setPower(0);
				sleep(3300);
				robot.arm.setPower(0);
				sleep(500);
				robot.lid.setPosition(0.55);
				robot.arm.setPower(1);
				robot.extension.setPower(-1);
				sleep(100);
				robot.arm.setPower(0);
				stopMotors();
			} else if (position.equals("right")) {
				rotateToAngle(110);
				moveUpDown(1);
				sleep(200);
				robot.arm.setPower(1);
				while (opModeIsActive() && Math.abs(robot.extension.getCurrentPosition() - closedExtension) < 2873) {
					telemetry.addData("extension", robot.extension.getCurrentPosition());
					telemetry.addData("closed", closedExtension);
					telemetry.update();
					robot.extension.setPower(1);
				}
				telemetry.addData("extension", robot.extension.getCurrentPosition());
				telemetry.addData("closed", closedExtension);
				telemetry.update();

				robot.extension.setPower(0);

				robot.arm.setPower(0);
				sleep(500);
				robot.arm.setPower(1);
				sleep(300);
				robot.arm.setPower(-1);
				sleep(300);
				robot.arm.setPower(1);
				sleep(500);
				robot.arm.setPower(-0.5);
				moveUpDown(-1);
				sleep(400);
				while (opModeIsActive() && robot.extension.getCurrentPosition() - closedExtension < 2873) {
					robot.extension.setPower(1);
				}
				robot.extension.setPower(0);
				sleep(3300);
				robot.arm.setPower(0);
				sleep(500);
				robot.lid.setPosition(0.55);
				robot.arm.setPower(1);
				robot.extension.setPower(-1);
				sleep(200);
				rotateToAngle(135);
				robot.arm.setPower(0);
				polarDrive(1, Math.PI / 3 - 0.35);
				sleep(1650);
				rotateToAngle(165);
				moveUpDown(1);
				sleep(1200);
				rotate(-1);
				sleep(700);
				robot.marker1.setPosition(0.3);
				moveLeftRight(-1);
				sleep(200);
				moveLeftRight(1);
				rotateToAngle(180);
				robot.marker1.setPosition(1);
				moveUpDown(-1);
				sleep(100);
				moveLeftRight(-1);
				sleep(450);
				moveUpDown(-1);
				sleep(2150);
				stopMotors();
			} else {
				rotateToAngle(145);

				robot.arm.setPower(1);
				while (opModeIsActive() && Math.abs(robot.extension.getCurrentPosition() - closedExtension) < 2820) {
					telemetry.addData("extension", robot.extension.getCurrentPosition());
					telemetry.addData("closed", closedExtension);
					telemetry.update();
					robot.extension.setPower(1);
				}
				telemetry.addData("extension", robot.extension.getCurrentPosition());
				telemetry.addData("closed", closedExtension);
				telemetry.update();

				robot.extension.setPower(0);

				robot.arm.setPower(0);
				sleep(500);
				robot.arm.setPower(1);
				sleep(300);
				robot.arm.setPower(-1);
				sleep(300);
				robot.arm.setPower(1);
				sleep(500);
				robot.arm.setPower(-0.5);
				while (opModeIsActive() && robot.extension.getCurrentPosition() - closedExtension < 2873) {
					robot.extension.setPower(1);
				}
				robot.extension.setPower(0);
				sleep(3300);
				robot.arm.setPower(0);
				sleep(500);
				robot.lid.setPosition(0.55);
				robot.arm.setPower(1);
				robot.extension.setPower(-1);
				sleep(200);
				rotateToAngle(135);
				robot.arm.setPower(0);
				moveLeftRight(-1);
				sleep(550);
				polarDrive(1, 2 * Math.PI / 2.5);
				sleep(1200);
				rotateToAngle(180);
				moveUpDown(1);
				sleep(620);
				moveLeftRight(-1);
				sleep(550);
				robot.marker1.setPosition(0.3);
				sleep(830);
				robot.marker1.setPosition(1);
				rotateToAngle(176);
				moveLeftRight(1);
				sleep(250);
				moveUpDown(-1);
				sleep(400);
				moveLeftRight(-1);
				sleep(250);
				moveUpDown(-1);
				sleep(2600);
				stopMotors();

			}

			stopMotors();
			while (opModeIsActive()) {
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2 - 1));
				sleep(100);
				robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(100 - 1));
				sleep(100);
				telemetry.addData("extension", robot.extension.getCurrentPosition());
				telemetry.addData("closed", closedExtension);
				telemetry.update();
			}
		/*while(opModeIsActive()&&robot.extension.getCurrentPosition()-closedExtension<2873){
			robot.extension.setPower(1);
		}
		robot.extension.setPower(0);*/
		}
	}
}
