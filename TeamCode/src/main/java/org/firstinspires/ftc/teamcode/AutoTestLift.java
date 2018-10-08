/*
   Holonomic/Mecanum concept autonomous program. Driving motors for TIME

   Robot wheel mapping:
          X FRONT X
        X           X
      X  FL       FR  X
              X
             XXX
              X
      X  BL       BR  X
        X           X
          X       X
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ExampleCode.HardwareSetupHolonomicExample;

@Autonomous(name="Concept: Holonomic AutoByTime", group="Concept")
//@Disabled
public class AutoTestLift extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    /* Define Hardware setup */
    HardwareTestLift robot     =   new HardwareTestLift();
    /**
     * Constructor
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

        LiftUp(DRIVE_POWER);
        CloseLatch();

        LiftDown(DRIVE_POWER);
        OpenLatch();



        HoldLift(); //need to create time parameter

    }//runOpMode

    /** Below: Sub-Methods for autonomous control...**/
    //set Drive Power variable
    double DRIVE_POWER = 1.0;

    //Control liftMotor
    private void LiftUp(double power) {
        // write the values to the motors

        while ( robot.motorArm.getCurrentPosition() < 3000)
        {
            robot.motorArm.setPower(power);         //note:  should be able to run motor to a set position
        }
    }

    private void LiftDown(double power)
    {
        // write the values to the motors
        while (robot.motorArm.getCurrentPosition() > 0.0 )
        {
            robot.motorArm.setPower(-power);
        }
    }

    private void HoldLift()
    {
        LiftUp(0);
    }

    private void HoldLiftTime(double power, long time) throws InterruptedException
    {
       // HoldLift(0, time);
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
