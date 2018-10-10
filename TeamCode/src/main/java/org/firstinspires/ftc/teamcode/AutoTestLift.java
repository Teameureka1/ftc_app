/*
    concept program for running liftArm and latch with autonomous programming.
    uses encoder to determine lift max and min
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ExampleCode.HardwareSetupHolonomicExample;

@Autonomous(name="Auto test", group="Concept")
//@Disabled
public class AutoTestLift extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    /* Define Hardware setup */
    HardwareTestLift robot     =   new HardwareTestLift();
    /**
     * Constructor allows calling this method from outside of this Class
     */
    public AutoTestLift() {
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);  //Initialize hardware from the HardwareHolonomic Setup

        //adds feedback telemetry to DS
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        /************************
         * Autonomous Code Below://
         *************************/

        //Lower robot from hook by running lift up to 3000
        OpenLatch();
        encoderLiftDrive(DRIVE_POWER,3000,3000); //may need to adjust timeOut time


        //continue with autonomous programming to move robot to scoring position
        

    }//runOpMode

    /** Below: Sub-Methods for autonomous control...**/
    //set Drive Power variable
    double DRIVE_POWER = 1.0;

    /*  Below is
     *  Method which performs relative moves, based on encoder counts.
     *  Encoders are NOT reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time  NOTE: Ample time must be given to allow for position to be obtained
     *  3) Driver stops the opmode running.
     */
    //Control liftMotor
    private void encoderLiftDrive(double speed,
                             double setPosition,
                             double timeoutS) {
        int newTarget;

        // Ensure that the opMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = robot.motorArm.getCurrentPosition() + (int)(setPosition);

            robot.motorArm.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            robot.motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.motorArm.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.motorArm.isBusy()))
            {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newTarget);
                telemetry.addData("Path2",  "Currently at %7d :%7d", robot.motorArm.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.motorArm.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

              sleep(250);   // optional pause after each move
        }
    }


 //Servo control
 //
 //
   private void CloseLatch()
    {
        robot.servoLatch.setPosition(.2); //note: uses servo instead of motor.
    }

    private void OpenLatch()
    {
        robot.servoLatch.setPosition(.5);
    }



}//TestAutoDriveByTime
