package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class CRServoTest extends LinearOpMode {
    CRServo clawServo;
    double servoPower;
    @Override
    public void runOpMode() {
        clawServo = hardwareMap.get(CRServo.class, "clawServo");
        clawServo.setPower(0);
        waitForStart();
        while (!isStopRequested()) {
            servoPower = gamepad1.right_stick_y;
            clawServo.setPower(servoPower);
        }
    }
}
