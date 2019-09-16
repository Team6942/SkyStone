package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class DriveTest extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    @Override
    public void runOpMode() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        waitForStart();
        backLeft.setPower(1);
        backRight.setPower(1);
        sleep(1000);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(1000);
        for (float i=0;i<=1;i+=0.01){
            backLeft.setPower(i);
            backRight.setPower(i);
            sleep(10);
        }
        sleep(1000);
        for (float i=1;i>=0;i-=0.01){
            backLeft.setPower(i);
            backRight.setPower(i);
            sleep(10);
        }
    }
}

