package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
@Autonomous
public class FieldTest extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;

    @Override
    public void runOpMode() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        initNavx();
        waitForStart();
        telemetry.log().clear();

        backLeft.setTargetPosition(-300);
        backRight.setTargetPosition(-300);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRight.setPower(.25);
        backLeft.setPower(.25);

        while (opModeIsActive() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("Path2", "%7d :%7d",
                    backLeft.getCurrentPosition(),
                    backRight.getCurrentPosition());
            telemetry.update();
        }
        backLeft.setPower(0);
        backRight.setPower(0);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive() && getZ() <= -45) {
            backLeft.setPower(-.2);
            backRight.setPower(.2);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    private void initNavx() {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = navxMicro;
        telemetry.log().add("do not move robot navx is calibrating");
        while (navxMicro.isCalibrating());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
    }
    private float getZ() {
        Orientation orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }
    private float getY() {
        Orientation orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.secondAngle;
    }
}