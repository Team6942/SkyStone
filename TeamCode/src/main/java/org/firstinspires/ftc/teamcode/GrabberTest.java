package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class GrabberTest extends LinearOpMode {
    DcMotor grabberMotor;
    double grabberPower;
    @Override
    public void runOpMode() {
        grabberMotor = hardwareMap.get(DcMotor.class, "claw");
        grabberMotor.setPower(0);
        grabberMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
            grabberPower = gamepad1.left_stick_y;
            grabberMotor.setPower(grabberPower);

            telemetry.addData("Position",  "%7d",
                    grabberMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
