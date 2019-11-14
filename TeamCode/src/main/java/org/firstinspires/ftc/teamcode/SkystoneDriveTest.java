package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous
public class SkystoneDriveTest extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor midShift;
    // vision vars and objects
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private static final String VUFORIA_KEY = "AfcHR0j/////AAABmfed/GCooE4+rJdSirhUBQVIWvgEZ1O83QNRD2QssWhg80bd+b3b7U/Q9EZJkStsXbOopP3SGyYEJjWzQ9TRkw8kdvYQz9CHB6M0aT6vAWJrkJnQnSxCjC7CLW53/IXfRR9qdK40wVw+RPu5xBST5bNHVbOxD8iuCx0ePgjfIrs+yC0r4VASI6c5vfyfkFixlV36nvNQjHwM/+Eyk8s10uzKTNwDmoVtEB/A5fBH+kqtG8r7KjPYtlVlhIn9dfLCgFdG5xAdAnBfeRvdFTfk1UqkKgvrQLdcU9WFkV24kegrjTPPigwiTB8RhlXLEdF8lhw3lcwg2Gb5Nev5D1PqujovEmxrJlkM5dU5HL/f44cs";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private List<Recognition> Recognitions;
    // navx declaration and vars
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;

    @Override
    public void runOpMode() {
        initNavx();
        initTFOD();
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        midShift.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        telemetry.log().clear();
        tfod.activate();

        backLeft.setTargetPosition(200);
        backRight.setTargetPosition(200);

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRight.setPower(.25);
        backLeft.setPower(.25);

        while (opModeIsActive() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("Path2",  "%7d :%7d",
                    backLeft.getCurrentPosition(),
                    backRight.getCurrentPosition());
            telemetry.update();
        }

        backLeft.setPower(0);
        backRight.setPower(0);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (getCurrentAngleToObject() == 0) {
            midShift.setPower(-.4);
        }
        while (getCurrentAngleToObject() >= 1) {
            midShift.setPower(-.4);
            telemetry.update();
        }

        midShift.setPower(0);

        backLeft.setTargetPosition(700);
        backRight.setTargetPosition(700);

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.getCurrentPosition() < 700 && backRight.getCurrentPosition() < 700 && opModeIsActive()) {
            backRight.setPower(.25);
            backLeft.setPower(.25);
        }

        backRight.setPower(0);
        backLeft.setPower(0);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setTargetPosition(-200);
        backRight.setTargetPosition(-200);

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
        while (getYaw() >= -90) {
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
    private void initTFOD() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    private double getCurrentAngleToObject() {
        Recognitions = tfod.getUpdatedRecognitions();
        if (Recognitions != null && !Recognitions.isEmpty()) {
            Recognition skystone = Recognitions.get(0);
            telemetry.addData("Angle to skystone", skystone.estimateAngleToObject(AngleUnit.DEGREES));
            return skystone.estimateAngleToObject(AngleUnit.DEGREES);
        }
        return 0;
    }
    private float getYaw() {
        Orientation orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }
}


