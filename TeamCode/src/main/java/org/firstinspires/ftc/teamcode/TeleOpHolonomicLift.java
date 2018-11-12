package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "TeleOp", group = "Comp")
//@Disabled
public class TeleOpHolonomicLift extends OpMode {

    Hardware10662 robot     =   new Hardware10662();

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
        robot.motorFrontLeft.setPower(FrontLeft);
        robot.motorBackLeft.setPower(BackLeft);
        robot.motorBackRight.setPower(BackRight);

      /*
       * Telemetry for debugging
       */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("botLift ",  "Currently at " + String.format("%.2", robot.botLift.getCurrentPosition()));
        telemetry.addData("bucketLift ",  "Currently at " + String.format("%.2", robot.bucketLift.getCurrentPosition()));
        telemetry.addData("Joy XL YL XR",  String.format("%.2f", gamepad1LeftX) + " " + String.format("%.2f", gamepad1LeftY) + " " +  String.format("%.2f", gamepad1RightX));
        telemetry.addData("f left pwr",  "front left  pwr: " + String.format("%.2f", FrontLeft));
        telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
        telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
        telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));

// bot lift Control - Uses dual buttons to control motor direction.
        // Uses combo of Encoder button sensor values to set upper and lower limits to protect motors from over-driving lift
        // May need to: Create additional encoder RESET button to correct for initial overdrive of encoder

        if (gamepad2.right_bumper && robot.sensorTouch.getState() == true )//bumper pressed AND button not pressed
        {
            robot.botLift.setPower(-gamepad2.right_trigger / 2.0); // let trigger run -motor
        }

        else if (!gamepad2.right_bumper && robot.botLift.getCurrentPosition() < 3500) //bumper NOT pressed AND encoder less than Max limit
        {
            robot.botLift.setPower(gamepad2.right_trigger / 2.0); //let trigger run +motor
        }

        else
        {
            robot.botLift.setPower(0.0); // else not trigger, then set to off or some value of 'hold' power

        }


 // bucketLift Uses joy stick left

    robot.bucketLift.setPower(gamepad2.left_stick_y);

        //sweeper control



        if (gamepad2.left_bumper) //button 'LB'collects
        {
            robot.servoSweep.setPosition(0.2);
        }

        else if (gamepad2.right_bumper) //button 'RB'Spits out
        {
            robot.servoSweep.setPosition(0.8);
        }
        else if (gamepad2.b)
        {
            robot.servoSweep.setPosition(0.5); //sweeper stoped
        }


        //Latch control
        if(gamepad2.x) //button 'x' will open
        {
            robot.servoLatch.setPosition(robot.OPEN);
        }
        else if (gamepad2.y) //button 'y' will close
        {
            robot.servoLatch.setPosition(robot.CLOSED);
        }

        //Encoder reset
        if(gamepad2.dpad_up)
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

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}