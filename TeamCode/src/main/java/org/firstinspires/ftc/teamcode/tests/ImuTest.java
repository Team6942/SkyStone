package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@TeleOp
public class ImuTest  extends LinearOpMode {
    BNO055IMU imu;
    DcMotor driveLeft, driveRight;

    @Override
    public void runOpMode() {
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        parameters.loggingEnabled      = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);
        telemetry.addLine("calibrating");
        telemetry.update();
        while (opModeIsActive() && !imu.isAccelerometerCalibrated());
        telemetry.clear();
        imu.startAccelerationIntegration(new Position(), new Velocity(), 10);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("velocity",imu.getVelocity());
            telemetry.update();
        }
    }
}
