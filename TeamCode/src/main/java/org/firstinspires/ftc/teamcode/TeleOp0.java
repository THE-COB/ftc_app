package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * Created by Rohan Mathur on 9/26/18.
 */
@TeleOp(name="Teleop", group="Pushbot")

public class TeleOp0 extends AvesAblazeOpmode {

	/* Declare OpMode members. */
	private ElapsedTime runtime = new ElapsedTime();
	float moveY;
	float moveX;
	float rotate;
	double position;
	@Override
	public void runOpMode() {
		robot.init(hardwareMap);
		waitForStart();
		while(opModeIsActive()) {
            telemetry.update();
            telemetry.addData("height", getLiftHeight());
            //Display coordinates and trackable
            if (resetCoordinates()) {
                telemetry.addData("Target", robot.currentTrackable.getName());
                // express position (translation) of robot in inches.
                VectorF translation = robot.lastLocation.getTranslation();
                //ArrayList translation[x, y, z]
                telemetry.addData("x", translation.get(0) / AvesAblazeHardwarePushbot.mmPerInch);
                telemetry.addData("y", translation.get(1) / AvesAblazeHardwarePushbot.mmPerInch);

                // Map rotation firstAngle: Roll; secondAngle: Pitch; thirdAngle: Heading
                Orientation rotation = Orientation.getOrientation(robot.lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Heading", rotation.thirdAngle);
            }
            else {
                telemetry.addData("Target", "none");
            }

            //Move Robot
			moveY = Range.clip(-gamepad1.left_stick_y, -1, 1);
			moveX = Range.clip(-gamepad1.left_stick_x, -1, 1);
			rotate = Range.clip(-gamepad1.right_stick_x, -1, 1);
			if ((Math.abs(moveY) > 0.25)) {
				moveUpDown(moveY);

			}
			else if (Math.abs(moveX) > 0.25)
				moveLeftRight(-moveX);
			else if (Math.abs(rotate) > 0.25) {
				rotate(-rotate);
			} else {
				moveUpDown(0);
				moveLeftRight(0);
				rotate(0);
			}

/*			if(moveY != 0 || moveX != 0){

			}*/

			//Move team marker mechanism
			if(gamepad1.left_trigger>0.1){
				robot.marker1.setPosition(0.7);
			}
			if(gamepad1.right_trigger>0.1){
				robot.marker1.setPosition(0);
			}

			else{
				//robot.marker.setPower(0.6);
			}

			//lift robot
			if(gamepad1.x){
				lift();
			}
			if(gamepad1.y){
				lower();
			}
			if(gamepad1.dpad_up){
				lift("up");
			}
			else if (gamepad1.dpad_down){
				lift("down");
			}
			else{
				lift("stop");
			}

			telemetry.addData("position", position);
			robot.servo0.setPower(position);
			if(gamepad1.dpad_left){
				position += 0.05;
				robot.servo0.setPower(position);
				while (opModeIsActive() && gamepad1.dpad_left) ;
			}
			else if(gamepad1.dpad_right) {
				position += 0.05;
				robot.servo0.setPower(position);
				while (opModeIsActive() && gamepad1.dpad_right) ;
			}
			if(gamepad1.left_trigger>0)
			robot.arm.setPower(gamepad1.left_trigger);
			else if(gamepad1.right_trigger>0)
				robot.arm.setPower(-gamepad1.right_trigger);
			else
				robot.arm.setPower(0);
		}


	}

}