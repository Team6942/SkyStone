package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

@TeleOp
public class VuforiaObjectRegTest extends LinearOpMode {
    private static final String VUFORIA_KEY = "AfcHR0j/////AAABmfed/GCooE4+rJdSirhUBQVIWvgEZ1O83QNRD2QssWhg80bd+b3b7U/Q9EZJkStsXbOopP3SGyYEJjWzQ9TRkw8kdvYQz9CHB6M0aT6vAWJrkJnQnSxCjC7CLW53/IXfRR9qdK40wVw+RPu5xBST5bNHVbOxD8iuCx0ePgjfIrs+yC0r4VASI6c5vfyfkFixlV36nvNQjHwM/+Eyk8s10uzKTNwDmoVtEB/A5fBH+kqtG8r7KjPYtlVlhIn9dfLCgFdG5xAdAnBfeRvdFTfk1UqkKgvrQLdcU9WFkV24kegrjTPPigwiTB8RhlXLEdF8lhw3lcwg2Gb5Nev5D1PqujovEmxrJlkM5dU5HL/f44cs";
    private VuforiaLocalizer vuforia;

    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor midShift;
    private double leftPower;
    private double rightPower;
    private double middlePower;
    private double drive;
    private double turn;
    private double leftStick;
    @Override
    public void runOpMode() {
/*        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");
        // set motor directions
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        midShift.setDirection(DcMotorSimple.Direction.FORWARD);*/
        
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables skystoneTrackables = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = skystoneTrackables.get(0);
        stoneTarget.setName("Stone Target");

        List<VuforiaTrackable> trackableElements = new ArrayList<VuforiaTrackable>();
        trackableElements.addAll(skystoneTrackables);

        waitForStart();

        skystoneTrackables.activate();

        while(!isStopRequested()) {
            OpenGLMatrix position = null;
            Orientation rotation;

/*            drive = gamepad1.right_stick_y;
            leftStick = gamepad1.left_stick_x;
            if(gamepad1.right_bumper) {
                middlePower =  leftStick;
                turn = 0;
            }
            else {
                turn = leftStick;
                middlePower = 0;
            }

            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);

            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            midShift.setPower(middlePower);*/

            if (((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
                position = ((VuforiaTrackableDefaultListener)stoneTarget.getListener()).getPose();
                rotation = Orientation.getOrientation(position, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                telemetry.addData("Target Rotation","%s,%s,%s",Math.floor(rotation.firstAngle),Math.floor(rotation.secondAngle),Math.floor(rotation.thirdAngle));
            }

            telemetry.update();
            telemetry.clear();
        }
        skystoneTrackables.deactivate();
    }
}
