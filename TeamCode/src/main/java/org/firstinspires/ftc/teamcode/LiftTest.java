package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Test program for running liftArm using encoder stop points
 * and
 * buttons for open/close latch servo
 */

@TeleOp(name="LiftTester", group="Examples")
//@Disabled
public class LiftTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    //motors

    DcMotor motorArm = null;

    //Servos
    Servo servoLatch = null;

    double CLOSED = 0.2;
    double OPEN = 0.5;


    @Override
    public void runOpMode() throws InterruptedException {
        //adds feedback telemetry to DS
        telemetry.addData("Status", "Initialized");
        telemetry.update();

         motorArm = hardwareMap.dcMotor.get("motorArm");
         servoLatch = hardwareMap.servo.get("servoLatch");
        // eg: Set the drive motor directions:
         motorArm.setDirection(DcMotor.Direction.FORWARD); // Can change based on motor configuration
        // reset Encoder to zero
         motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

         //motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // runs motor faster than when set to RUN_USING_ENCODER
         motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Motor slower but more accurate encoder reading

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Encoder", motorArm.getCurrentPosition());
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        /************************
         * TeleOp Code Below://
         *************************/

        while (opModeIsActive()) {  // run until the end of the match (driver presses STOP)
            // Display running time and Encoder value
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Encoder Clicks", + motorArm.getCurrentPosition());
            telemetry.update();

            // Arm Control - Uses dual buttons to control motor direction.
            // Uses Encoder values to set upper and lower limits to protect motors from over-driving lift
            // May need to: Create additional encoder RESET button to correct for initial overdrive of encoder
//
            if (gamepad1.right_bumper && motorArm.getCurrentPosition() > -10) //bumper pressed AND encoder greater that lower limit
            {
                motorArm.setPower(-gamepad1.right_trigger / 2.0); // let trigger run -motor
            }
//
            else if (!gamepad1.right_bumper && motorArm.getCurrentPosition() < 3000) //bumper NOT pressed AND encoder less than Max limit
            {
                motorArm.setPower(gamepad1.right_trigger / 2.0); //let trigger run +motor
            }

            else
            {
                motorArm.setPower(0.0); // else not trigger, then set to off or some value of 'hold' power
            }

            //Latch control
            if(gamepad1.a) //button 'a' will open
            {
                servoLatch.setPosition(OPEN);
            }
            else if (gamepad1.b) //button 'b' will close
            {
                servoLatch.setPosition(CLOSED);
            }

            //Encoder reset
            if(gamepad1.dpad_up)
            {
                // reset Encoder to zero
                motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
