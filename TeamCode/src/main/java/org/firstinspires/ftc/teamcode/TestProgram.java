package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

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
        VuforiaTrackable stoneElement = skystoneTrackables.get(0);

        waitForStart();

        skystoneTrackables.activate();

        while(opModeIsActive()) {

        }
    }
}
