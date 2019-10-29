package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class GamepadTest extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor midShift;
    DcMotor liftLeft;
    DcMotor liftRight;
    DcMotor claw;
    double leftPower;
    double rightPower;
    double middlePower;
    double clawPower;
    double drive;
    double turn;
    double ls;
    double rt;

    @Override
    public void runOpMode() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");
        liftLeft = hardwareMap.get(DcMotor.class,"liftLeft");
        liftRight = hardwareMap.get(DcMotor.class,"liftRight");
        claw = hardwareMap.get(DcMotor.class,"claw");

        liftLeft.setPower(0);
        liftRight.setPower(0);
        claw.setPower(0);

        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);

        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw.setDirection(DcMotorSimple.Direction.FORWARD);

        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        midShift.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        while(!isStopRequested()) {
            rt = gamepad1.right_trigger;
            turn = gamepad1.right_stick_x;

            if (gamepad1.a) {
                clawPower = rt;
            }
            else if (gamepad1.b) {
                clawPower = -rt;
            }
            else
            {
                clawPower = 0;
            }

            if (gamepad1.dpad_up) {
                drive = rt;
            }
            else if (gamepad1.dpad_down) {
                drive = -rt;
            }
            else if (gamepad1.dpad_right) {
                middlePower = rt;
            }
            else if (gamepad1.dpad_left) {
                middlePower = -rt;
            }
            else {
                drive = 0;
                middlePower = 0;
            }

            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);

            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            midShift.setPower(middlePower);

            claw.setPower(clawPower);
        }
    }
}

