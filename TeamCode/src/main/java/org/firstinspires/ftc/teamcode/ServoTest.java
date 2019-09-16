package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ServoTest extends LinearOpMode {
    Servo testServo;
    @Override
    public void runOpMode() {
        testServo = hardwareMap.get(Servo.class, "testServo");
        waitForStart();
        testServo.setPosition(0);
        for (float i=0;i<=0;i+=0.2) {
            testServo.setPosition(1);
            sleep(500);
        }
    }
}
