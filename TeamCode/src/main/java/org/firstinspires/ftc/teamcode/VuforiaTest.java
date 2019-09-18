package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
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
public class VuforiaTest extends LinearOpMode {
    private static final String VUFORIA_KEY = "AfcHR0j/////AAABmfed/GCooE4+rJdSirhUBQVIWvgEZ1O83QNRD2QssWhg80bd+b3b7U/Q9EZJkStsXbOopP3SGyYEJjWzQ9TRkw8kdvYQz9CHB6M0aT6vAWJrkJnQnSxCjC7CLW53/IXfRR9qdK40wVw+RPu5xBST5bNHVbOxD8iuCx0ePgjfIrs+yC0r4VASI6c5vfyfkFixlV36nvNQjHwM/+Eyk8s10uzKTNwDmoVtEB/A5fBH+kqtG8r7KjPYtlVlhIn9dfLCgFdG5xAdAnBfeRvdFTfk1UqkKgvrQLdcU9WFkV24kegrjTPPigwiTB8RhlXLEdF8lhw3lcwg2Gb5Nev5D1PqujovEmxrJlkM5dU5HL/f44cs";
    private VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables skystoneTrackables = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = skystoneTrackables.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = skystoneTrackables.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = skystoneTrackables.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = skystoneTrackables.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = skystoneTrackables.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = skystoneTrackables.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = skystoneTrackables.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = skystoneTrackables.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = skystoneTrackables.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = skystoneTrackables.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = skystoneTrackables.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = skystoneTrackables.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = skystoneTrackables.get(12);
        rear2.setName("Rear Perimeter 2");

        List<VuforiaTrackable> trackableElements = new ArrayList<VuforiaTrackable>();
        trackableElements.addAll(skystoneTrackables);

        waitForStart();

        skystoneTrackables.activate();

        while(!isStopRequested()) {
            boolean isElementVisible= false;
            String visibleElementName = null;
            OpenGLMatrix position = null;
            VectorF translation;
            Orientation rotation;

            for (VuforiaTrackable trackableElement : trackableElements) {
                if (((VuforiaTrackableDefaultListener)trackableElement.getListener()).isVisible()) {
                    isElementVisible = true;
                    visibleElementName = trackableElement.getName();
                    position = ((VuforiaTrackableDefaultListener)trackableElement.getListener()).getPose();
                }
            }

            if (isElementVisible) {
                telemetry.addData("Most Visible Target", visibleElementName);
                translation = position.getTranslation();
                rotation = Orientation.getOrientation(position, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                telemetry.addData("Target Translation","%s,%s,%s",Math.floor(translation.get(0)),Math.floor(translation.get(1)),Math.floor(translation.get(2)));
                telemetry.addData("Target Rotation","%s,%s,%s",Math.floor(rotation.firstAngle),Math.floor(rotation.secondAngle),Math.floor(rotation.thirdAngle));
            }
            else {
                telemetry.addData("Most Visible Target", "none");
            }

            telemetry.update();
            telemetry.clear();

            isElementVisible = false;
        }
        skystoneTrackables.deactivate();
    }
}
