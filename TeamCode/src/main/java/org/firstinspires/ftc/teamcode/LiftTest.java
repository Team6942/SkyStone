package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class LiftTest extends LinearOpMode {
    DcMotor liftLeft;
    DcMotor liftRight;
    float liftPower;
    @Override
    public void runOpMode() {
        liftLeft = hardwareMap.get(DcMotor.class,"liftLeft");
        liftRight = hardwareMap.get(DcMotor.class,"liftRight");
        liftLeft.setPower(0);
        liftRight.setPower(0);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (!isStopRequested()) {
        liftPower = gamepad1.left_stick_y;
        liftLeft.setPower(liftPower);
        liftRight.setPower(liftPower);

        telemetry.addData("Position",  "%7d :%7d",
        liftLeft.getCurrentPosition(),
        liftRight.getCurrentPosition());
        telemetry.update();
        }
    }
}
