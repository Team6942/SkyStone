package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

// old code from 2018-2019 competition use only for reference
@Disabled
@TeleOp
public class DriveTest extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor midShift;
    double leftPower;
    double rightPower;
    double middlePower;
    double drive;
    double turn;
    @Override
    public void runOpMode() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");

        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        midShift.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(!isStopRequested()) {
            drive = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_y;
            middlePower = gamepad1.right_stick_x;

            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);

            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            midShift.setPower(middlePower);
        }
    }
}

