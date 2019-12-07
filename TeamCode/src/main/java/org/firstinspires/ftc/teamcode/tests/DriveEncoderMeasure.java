package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@Disabled
@TeleOp
public class DriveEncoderMeasure extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    private double leftPower;
    private double rightPower;
    private double drive;
    private double turn;
    @Override
    public void runOpMode()  {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {
            drive = gamepad1.right_stick_y;
            turn = gamepad1.left_stick_x;

            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);

            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);

            telemetry.addData("Path2",  "%7d :%7d",
                    backLeft.getCurrentPosition(),
                    backRight.getCurrentPosition());
            telemetry.update();
        }
    }
}
