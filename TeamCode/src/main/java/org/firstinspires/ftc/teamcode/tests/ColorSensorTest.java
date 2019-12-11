package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp
public class ColorSensorTest extends LinearOpMode {
    ColorSensor colorSensor;
    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        telemetry.setAutoClear(true);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("red",colorSensor.red());
            telemetry.addData("green",colorSensor.green());
            telemetry.addData("blue",colorSensor.blue());
            telemetry.update();
        }
    }
}
