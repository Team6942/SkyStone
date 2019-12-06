package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends LinearOpMode {
    Servo clawServo;
    double servoPosition;
    @Override
    public void runOpMode() {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setPosition(0);
        waitForStart();
        while (!isStopRequested()) {
            while (gamepad1.a) {
                servoPosition += .1;
                clawServo.setPosition(servoPosition);
            }
            while (gamepad1.b) {
                servoPosition -= .1;
                clawServo.setPosition(servoPosition);
            }
        }
    }
}
