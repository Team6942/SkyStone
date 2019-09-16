package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

// start teleop
@TeleOp
public class TestProgram extends LinearOpMode {
    VuforiaLocalizer vuforia;
    @Override
    public void runOpMode() {
        // get view id
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        // init parms
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // set parms
        parameters.vuforiaLicenseKey = "AfcHR0j/////AAABmfed/GCooE4+rJdSirhUBQVIWvgEZ1O83QNRD2QssWhg80bd+b3b7U/Q9EZJkStsXbOopP3SGyYEJjWzQ9TRkw8kdvYQz9CHB6M0aT6vAWJrkJnQnSxCjC7CLW53/IXfRR9qdK40wVw+RPu5xBST5bNHVbOxD8iuCx0ePgjfIrs+yC0r4VASI6c5vfyfkFixlV36nvNQjHwM/+Eyk8s10uzKTNwDmoVtEB/A5fBH+kqtG8r7KjPYtlVlhIn9dfLCgFdG5xAdAnBfeRvdFTfk1UqkKgvrQLdcU9WFkV24kegrjTPPigwiTB8RhlXLEdF8lhw3lcwg2Gb5Nev5D1PqujovEmxrJlkM5dU5HL/f44cs";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        // build instance
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        // load trackables
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

        List<VuforiaTrackable> trackablesList = new ArrayList<VuforiaTrackable>();
        trackablesList.addAll(skystoneTrackables);

        waitForStart();

        skystoneTrackables.activate();

        while(opModeIsActive()) {
            boolean isElementVisable = false;
            for (VuforiaTrackable trackableElement : trackablesList) {
                if(((VuforiaTrackableDefaultListener)trackableElement.getListener()).isVisible()) {
                    isElementVisable = true;
                }
                if (isElementVisable) {
                    telemetry.clear();
                    telemetry.addData("Target Is Visable",trackableElement.getName());
                    isElementVisable = false;
                }
            }
            telemetry.update();
        }
    }
}
