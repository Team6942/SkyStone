package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.lang.Math;

@Autonomous
public class AutonomousMode extends LinearOpMode {
    // use appropriate key
    private static final String VUFORIA_KEY = "AfcHR0j/////AAABmfed/GCooE4+rJdSirhUBQVIWvgEZ1O83QNRD2QssWhg80bd+b3b7U/Q9EZJkStsXbOopP3SGyYEJjWzQ9TRkw8kdvYQz9CHB6M0aT6vAWJrkJnQnSxCjC7CLW53/IXfRR9qdK40wVw+RPu5xBST5bNHVbOxD8iuCx0ePgjfIrs+yC0r4VASI6c5vfyfkFixlV36nvNQjHwM/+Eyk8s10uzKTNwDmoVtEB/A5fBH+kqtG8r7KjPYtlVlhIn9dfLCgFdG5xAdAnBfeRvdFTfk1UqkKgvrQLdcU9WFkV24kegrjTPPigwiTB8RhlXLEdF8lhw3lcwg2Gb5Nev5D1PqujovEmxrJlkM5dU5HL/f44cs";
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables skystoneTrackables;
    private VuforiaTrackable stoneTarget;
    private OpenGLMatrix position = null;
    private Orientation rotation;
    // gyro objects
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
    // motors
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor midShift;
    private DcMotor liftLeft;
    private DcMotor liftRight;
    private DcMotor claw;
    private Servo pushServo;
    // drift vars
    private float currentDrift;
    private float counterDriftPower;
    // claw vars
    private boolean isSkystoneGrabbed = false;
    private float lastClawPosition;

    @Override
    public void runOpMode() {
        initNavx();
        // get hardware objects
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");
        liftLeft = hardwareMap.get(DcMotor.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotor.class, "liftRight");
        pushServo = hardwareMap.get(Servo.class,"pushServo");
        claw = hardwareMap.get(DcMotor.class,"claw");

        // set motor modes and directions
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        midShift.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // open the claw
        claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw.setTargetPosition(-300);
        claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setupVuforia();

        waitForStart();
        skystoneTrackables.activate();
        // move forward 900 ticks
        backRight.setPower(.35);
        backLeft.setPower(.35);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setTargetPosition(900);
        backRight.setTargetPosition(900);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy() && backRight.isBusy() && opModeIsActive());

        backLeft.setPower(0);
        backRight.setPower(0);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midShift.setPower(-.4);
        // move left until a skystone is detected
        while (getAngle() == 0 && opModeIsActive()) {
            currentDrift = getYaw();
            counterDriftPower = (currentDrift / 90);
            backLeft.setPower(counterDriftPower);
            backRight.setPower(-counterDriftPower);
        }
        // center with the block
        if (getAngle() > .2) {
            midShift.setPower(-.45);

            midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            midShift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (midShift.getCurrentPosition() > -170 && opModeIsActive()) {
                currentDrift = getYaw();
                counterDriftPower = (currentDrift / 90) * 2;
                backLeft.setPower(counterDriftPower);
                backRight.setPower(-counterDriftPower);
            }

        } else if (getAngle() < -.1) {
            midShift.setPower(-.45);

            midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            midShift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (midShift.getCurrentPosition() > -280 && opModeIsActive()) {
                currentDrift = getYaw();
                counterDriftPower = (currentDrift / 90) * 2;
                backLeft.setPower(counterDriftPower);
                backRight.setPower(-counterDriftPower);
            }
        } else {
            midShift.setPower(-.45);

            midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            midShift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (midShift.getCurrentPosition() > -270 && opModeIsActive()) {
                currentDrift = getYaw();
                counterDriftPower = (currentDrift / 90) * 2;
                backLeft.setPower(counterDriftPower);
                backRight.setPower(-counterDriftPower);
            }
        }
        // move forward 200 ticks to get in grabbing range of the block
        midShift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        midShift.setPower(0);
        backRight.setPower(.25);
        backLeft.setPower(.25);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setTargetPosition(200);
        backRight.setTargetPosition(200);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {
            currentDrift = getYaw();
            counterDriftPower = (currentDrift / 90) * 2;
            backLeft.setPower(counterDriftPower + .25);
            backRight.setPower(-counterDriftPower + .25);
        }
        // drop arm
        pushServo.setPosition(0);
        sleep(500);
        pushServo.setPosition(90);
        // close claw on skystone
        claw.setTargetPosition(0);
        claw.setPower(.7);
        lastClawPosition = claw.getCurrentPosition();
        while (!isSkystoneGrabbed) {
            if (!(claw.getCurrentPosition() >= lastClawPosition + 5) || !(claw.getCurrentPosition() <= lastClawPosition - 5)) {
                isSkystoneGrabbed = true;
            }
        }
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw.setPower(0);

        // back up 200 ticks
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setTargetPosition(-200);
        backRight.setTargetPosition(-200);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy() && backRight.isBusy() && opModeIsActive()) {
            currentDrift = getYaw();
            counterDriftPower = (currentDrift / 90) * 2;
            backLeft.setPower(counterDriftPower + .25);
            backRight.setPower(-counterDriftPower + .25);
        }

        backRight.setPower(0);
        backLeft.setPower(0);
    }
    private void setupVuforia() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        skystoneTrackables = this.vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = skystoneTrackables.get(0);
        stoneTarget.setName("Stone Target");
    }
    private float getAngle() {
        float angle;
        if (((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()) {
            position = ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).getPose();
            rotation = Orientation.getOrientation(position, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            telemetry.addData("Target Rotation", "%s,%s,%s", Math.floor(rotation.firstAngle), Math.floor(rotation.secondAngle), Math.floor(rotation.thirdAngle));
        }
        if (rotation == null) {
            angle = 0;
        } else {
            angle = rotation.secondAngle;
        }
        return angle;
    }
    private void initNavx() {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        telemetry.log().add("do not move robot navx is calibrating");
        while (navxMicro.isCalibrating() && opModeIsActive());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
    }
    private float getYaw() {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }
}
