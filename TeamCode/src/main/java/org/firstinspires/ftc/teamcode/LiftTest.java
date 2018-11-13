package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Test program for running liftArm using encoder stop points
 * and
 * buttons for open/close latch servo
 */

@TeleOp(name="LiftTester", group="Examples")
@Disabled
public class LiftTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    /* Define Hardware setup */
    Hardware10662 robot     =   new Hardware10662();
    /**
     * Constructor allows calling this method from outside of this Class
     */
    public LiftTest() {
    }   

    @Override
    public void runOpMode() throws InterruptedException {
        //adds feedback telemetry to Dc
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);  //Initialize hardware from the HardwareHolonomic Setup
        
        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Encoder", robot.botLift.getCurrentPosition());
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
            telemetry.addData("Encoder Clicks", + robot.botLift.getCurrentPosition());
            telemetry.update();

            // Arm Control - Uses dual buttons to control motor direction.
            // Uses Encoder values to set upper and lower limits to protect motors from over-driving lift
            // May need to: Create additional encoder RESET button to correct for initial overdrive of encoder
//
            if (gamepad1.right_bumper  ) //bumper pressed AND limit switch NOT pressed - (if the digital channel returns true it's HIGH and the button is unpressed.)
            {
                robot.botLift.setPower(-gamepad1.right_trigger / 2.0); // let trigger run -motor
            }
//
            else if (!gamepad1.right_bumper && robot.botLift.getCurrentPosition() < 3000) //bumper NOT pressed AND encoder less than Max limit

            {
                robot.botLift.setPower(gamepad1.right_trigger / 2.0); //let trigger run +motor
            }

            else
            {
                robot.botLift.setPower(0.0); // else not trigger, then set to off or some value of 'hold' power
            }

            //Latch control
            if(gamepad1.a) //button 'a' will open
            {
                robot.servoLatch.setPosition(robot.OPEN);
            }
            else if (gamepad1.b) //button 'b' will close
            {
                robot.servoLatch.setPosition(robot.CLOSED);
            }

            //Encoder reset
            if(gamepad1.dpad_up)
            {
                // reset Encoder to zero
                robot.botLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
