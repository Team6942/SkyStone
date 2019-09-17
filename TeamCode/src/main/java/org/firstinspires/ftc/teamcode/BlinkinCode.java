package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
public class BlinkinCode extends LinearOpMode{
    private RevBlinkinLedDriver blinkin;

    public void runOpMode() {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            }
            if (gamepad1.b) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }
            if (gamepad1.y){
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
            }
        }
    }
}