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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ExampleCode.HardwareSetupHolonomicExample;

@Autonomous(name="AutoByTime", group="Comp")
//@Disabled
public class HolonomicAutoDriveByTime10662 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    int     botLiftHoldPosition;         // reading of arm position when buttons released to hold
    double  slopeVal         = 2000.0;   // increase or decrease to perfect

    /* Define Hardware setup */
    Hardware10662 robot     =   new Hardware10662();    /**
     * Constructor
     */
    public HolonomicAutoDriveByTime10662() {
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

        //BotLift(-0.8,2000);

       // robot.botLift.setPower(0.5); // l
       // botLiftHoldPosition = robot.botLift.getCurrentPosition(); // update hold position to current position
       // robot.botLift.setPower((double) (botLiftHoldPosition - robot.botLift.getCurrentPosition()) / slopeVal);   // Note that if the lift is lower than desired position,

       // Latch(robot.OPEN);
       // Thread.sleep(2500);

       // StrafeLeft(DRIVE_POWER, 2000);
        //StopDrivingTime(1000);

        //Sweep(DRIVE_POWER);
        //sleep(2000);

        DriveForwardTime(DRIVE_POWER, 3500);
        StopDrivingTime(1000);
       // DriveForwardTime(-DRIVE_POWER, 1000); //neg power drives backwards
       // StopDrivingTime(1000);

        //StrafeLeft(DRIVE_POWER, 1000);
        //StopDrivingTime(1000);
        //StrafeRight(DRIVE_POWER, 1000);
        //SpinLeft(DRIVE_POWER, 1000);
        //StopDrivingTime(1000);
        //SpinRight(DRIVE_POWER, 1000);
        //StopDrivingTime(1000);

        //Sweep(DRIVE_POWER);
        //sleep(2000);

        //robot.botLift(0.5,1000);



        StopDriving();

    }//runOpMode

    /** Below: Basic Drive Methods used in Autonomous code...**/
    //set Drive Power variable
    double DRIVE_POWER = 1.0;

    public void DriveForward(double power)
    {
        // write the values to the motors
        robot.motorFrontRight.setPower(power);//still need to test motor directions for desired movement
        robot.motorFrontLeft.setPower(power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(power);
    }

    public void DriveForwardTime(double power, long time) throws InterruptedException
    {
        DriveForward(power);
        Thread.sleep(time);
    }

    public void StopDriving()
    {
        DriveForward(0);
    }

    public void StopDrivingTime(long time) throws InterruptedException
    {
        DriveForwardTime(0, time);
    }

    public void StrafeLeft(double power, long time) throws InterruptedException
    {
        // write the values to the motors
        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(-power);
        robot.motorBackRight.setPower(-power);
        robot.motorBackLeft.setPower(power);
        Thread.sleep(time);
    }

    public void StrafeRight(double power, long time) throws InterruptedException
    {
        StrafeLeft(-power, time);
    }

    public void SpinRight (double power, long time) throws InterruptedException
    {
        // write the values to the motors
        robot.motorFrontRight.setPower(-power);
        robot.motorFrontLeft.setPower(power);
        robot.motorBackRight.setPower(-power);
        robot.motorBackLeft.setPower(power);
        Thread.sleep(time);
    }

    public void SpinLeft (double power, long time) throws InterruptedException
    {
        SpinRight(-power, time);
    }

   public void BotLift (double power, long time) throws InterruptedException
    {
        robot.botLift.setPower(power);
    }

    public void Latch (double pos)

    {
        robot.servoLatch.setPosition(pos);
    }

    public void Sweep (double pos)
    {
        robot.servoSweep.setPosition(pos);
    }
    /*** Currently no Servo configured in Holonomic Hardware setup

    public void RaiseArm()
    {
        robot.armServo.setPosition(.8); //note: uses servo instead of motor.
    }

    public void LowerArm()
    {
        robot.armServo.setPosition(.2);
    }
*/


}//TestAutoDriveByTime
