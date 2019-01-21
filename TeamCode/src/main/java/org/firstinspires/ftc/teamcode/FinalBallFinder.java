package org.firstinspires.ftc.teamcode;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

/**
 * Created by Rohan Mathur on 1/9/19.
 */
@TeleOp(name = "ball finder", group = "testers")
public class FinalBallFinder extends AvesAblazeOpmode{

	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		if (opModeIsActive()) {
			initVuforia();


			if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
				initTfod();
			} else {
				telemetry.addData("Sorry!", "This device is not compatible with TFOD");
			}

			/** Wait for the game to begin */
			telemetry.addData(">", "Press Play to start tracking");
			telemetry.update();
			waitForStart();

			if (opModeIsActive()) {
				/** Activate Tensor Flow Object Detection. */
				if (tfod != null) {
					tfod.activate();
				}

				while (opModeIsActive()) {
					if (tfod != null) {
						// getUpdatedRecognitions() will return null if no new information is available since
						// the last time that call was made.
						List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
						if (updatedRecognitions != null) {
							Recognition rec0;
							Recognition rec1;
							String position = "";
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
									if (rec0.getLeft() > rec1.getLeft()) {
										position = "Center";
									} else {
										position = "Left";
									}
								} else if (rec1.getLabel().equals(LABEL_GOLD_MINERAL)) {
									if (rec1.getLeft() > rec0.getLeft()) {
										position = "Center";
									} else {
										position = "Left";
									}
								} else {
									position = "Right";
								}
							}
							telemetry.addData("Gold Mineral Position", position);
							telemetry.update();
							if (updatedRecognitions.size() == 1) {
								rotate(0.01);
							}
							stopMotors();
						}
					}

				}
			}

			if (tfod != null) {
				tfod.shutdown();
			}
		}
	}
}
