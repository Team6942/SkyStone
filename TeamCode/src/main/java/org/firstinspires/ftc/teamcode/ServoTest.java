package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ServoTest extends LinearOpMode {
    Servo clawServo;
    @Override
    public void runOpMode() {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setPosition(0);
        waitForStart();
        clawServo.setPosition(.5);
        sleep(1000);

    }
}
