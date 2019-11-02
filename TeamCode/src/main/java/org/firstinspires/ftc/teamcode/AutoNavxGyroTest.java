package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous
public class AutoNavxGyroTest extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(true);
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        initNavx();
        waitForStart();
        while (opModeIsActive() && getYaw() <= 180) {
            backLeft.setPower(.3);
            backRight.setPower(-.3);
            telemetry.addData("gyro",getYaw());
            telemetry.update();
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
