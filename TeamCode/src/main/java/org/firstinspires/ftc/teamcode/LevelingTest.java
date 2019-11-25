package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class LevelingTest extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor midShift;
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
    float currentDrift;
    float counterDriftPower;
    float midShiftPower;
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(true);
        initNavx();
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        midShift.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            midShiftPower = gamepad1.left_stick_y;
            currentDrift = getYaw();
            counterDriftPower = currentDrift / 90;
            telemetry.addData("power for left motor",counterDriftPower);
            telemetry.addData("yaw",currentDrift);
            telemetry.update();
            midShift.setPower(midShiftPower);
            backLeft.setPower(counterDriftPower);
            backRight.setPower(-counterDriftPower);
        }

    }
    private void initNavx() {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        telemetry.log().add("do not move robot navx is calibrating");
        while (navxMicro.isCalibrating());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
    }
    private float getYaw() {
        Orientation orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }
}
