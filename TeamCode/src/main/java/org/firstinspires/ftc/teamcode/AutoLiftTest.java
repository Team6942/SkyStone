package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AutoLiftTest extends LinearOpMode {
    DcMotor liftLeft;
    DcMotor liftRight;
    int leftTargetPosition;
    int rightTargetPosition;
    @Override
    public void runOpMode() {
        liftLeft = hardwareMap.get(DcMotor.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotor.class, "liftRight");
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        liftLeft.setTargetPosition(460);
        liftRight.setTargetPosition(460);

        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftLeft.setPower(1);
        liftRight.setPower(1);

        while (opModeIsActive() && liftLeft.isBusy() && liftRight.isBusy()) {
            telemetry.addData("Path2",  "%7d :%7d",
                    liftLeft.getCurrentPosition(),
                    liftRight.getCurrentPosition());
            telemetry.update();
        }

        liftLeft.setPower(0);
        liftRight.setPower(0);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }
}
