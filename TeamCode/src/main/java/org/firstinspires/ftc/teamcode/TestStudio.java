package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="testStudio", group="pushbot")
public class TestStudio extends OpMode {

	@Override
	public void init() {
		telemetry.addData("TestInit", "inIt Works");
		telemetry.update();
	}

	@Override
	public void loop() {
		telemetry.update();
		telemetry.addData("testLoop","loop works");
		telemetry.update();
	}
}
