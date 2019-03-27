package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "TeleOp", group = "Comp")
//@Disabled
public class TeleOpHolonomicLift extends OpMode {

    Hardware10662 robot     =   new Hardware10662();

    int     botLiftHoldPosition;         // reading of arm position when buttons released to hold
    int     bucketLiftHoldPosition;      // reading of bucketLift position
    double  slopeVal         = 2000.0;   // increase or decrease to perfect

    /**
     * Constructor
     */
    public TeleOpHolonomicLift() {
    }

    @Override
    public void init() {
      /*
       * Use the hardwareMap to get the dc motors and servos by name. Note
       * that the names of the devices must match the names used when you
       * configured your robot and created the configuration file.
       */
        robot.init(hardwareMap);  //Initialize hardware from the HardwareHolonomic Setup
    }

    @Override
    public void loop() {


        // left stick controls direction
        // right stick X controls rotation

        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        robot.motorFrontRight.setPower(FrontRight);
        robot.motorFrontLeft.setPower(-FrontLeft);
        robot.motorBackLeft.setPower(-BackLeft);
        robot.motorBackRight.setPower(BackRight);


       //Telemetry for debugging  // !!GETTING A FORMAT CONVERSION ERROR SO THIS IS CURRENTY COMMENTED OUT!!!
/*
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("botLift ",  "Currently at " + String.format("%.2", robot.botLift.getCurrentPosition()));
        telemetry.addData("bucketLift ",  "Currently at " + String.format("%.2", robot.bucketLift.getCurrentPosition()));
        telemetry.addData("Joy XL YL XR",  "Joy at " + String.format("%.2f", gamepad1LeftX) + " " + String.format("%.2f", gamepad1LeftY) + " " +  String.format("%.2f", gamepad1RightX));
        telemetry.addData("f left pwr",  "front left  pwr: " + String.format("%.2f", FrontLeft));
        telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
        telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
        telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));
*/

// bot lift Control - Uses dual buttons to control motor direction.
        // Uses combo of Encoder button sensor values to set upper and lower limits to protect motors from over-driving lift
        // May need to: Create additional encoder RESET button to correct for initial overdrive of encoder

        if (gamepad1.right_bumper && gamepad1.right_trigger >0.2 )//bumper pressed AND button not pressed
        {
            //Hook down
            robot.botLift.setPower(-gamepad1.right_trigger / 2.0); // let trigger run -motor
            botLiftHoldPosition = robot.botLift.getCurrentPosition(); // update hold position to current position
        }

        else if (!gamepad1.right_bumper && gamepad1.right_trigger >0.2 ) //bumper NOT pressed AND encoder less than Max limit
        {
            //hook up
            robot.botLift.setPower(gamepad1.right_trigger / 2.0); //let trigger run +motor
            botLiftHoldPosition = robot.botLift.getCurrentPosition(); // update hold position to current position
        }

        else
        {
            robot.botLift.setPower((double) (botLiftHoldPosition - robot.botLift.getCurrentPosition()) / slopeVal);   // Note that if the lift is lower than desired position,
        }


    // bucketLift Using D-Padr

    if (gamepad2.dpad_up )
    {
        robot.bucketLift.setPower(0.5); // bucket up
        bucketLiftHoldPosition = robot.bucketLift.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position
    }
    else if (gamepad2.dpad_down )
    {
        robot.bucketLift.setPower(-0.5); // bucket down
        bucketLiftHoldPosition = robot.bucketLift.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position
    }
    else
    {
        robot.bucketLift.setPower((double) (bucketLiftHoldPosition - robot.bucketLift.getCurrentPosition()) / slopeVal);   // Note that if the lift is lower than desired position,
    }


    //sweeper control
        if (gamepad2.left_bumper) //button 'LB'collects
        {
          robot.motorBucketSweep.setPower(0.5);
        }

        else if (gamepad2.right_bumper) //button 'RB'Spits out
        {
            robot.motorBucketSweep.setPower(-0.5);
        }
        else if (gamepad2.b)
        {
          robot.motorBucketSweep.setPower(0);
        }


    //Latch control
        if(gamepad1.x) //button 'x' will open
        {
            robot.servoLatch.setPosition(robot.OPEN);
        }
        else if (gamepad1.y) //button 'y' will close
        {
            robot.servoLatch.setPosition(robot.CLOSED);
        }

        //Encoder reset
        if(gamepad1.dpad_up)
        {
            // reset Encoder to zero
            robot.botLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

       // idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

    }
///////////////////////////////////////

    @Override
    public void stop() {

    }

}