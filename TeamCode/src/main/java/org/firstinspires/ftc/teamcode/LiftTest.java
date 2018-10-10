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

    /* Define Hardware setup */
    HardwareTestLift robot     =   new HardwareTestLift();
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
        telemetry.addData("Encoder", robot.motorArm.getCurrentPosition());
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
            telemetry.addData("Encoder Clicks", + robot.motorArm.getCurrentPosition());
            telemetry.update();

            // Arm Control - Uses dual buttons to control motor direction.
            // Uses Encoder values to set upper and lower limits to protect motors from over-driving lift
            // May need to: Create additional encoder RESET button to correct for initial overdrive of encoder
//
            if (gamepad1.right_bumper && robot.motorArm.getCurrentPosition() > -10) //bumper pressed AND encoder greater that lower limit
            {
                robot.motorArm.setPower(-gamepad1.right_trigger / 2.0); // let trigger run -motor
            }
//
            else if (!gamepad1.right_bumper && robot.motorArm.getCurrentPosition() < 3000) //bumper NOT pressed AND encoder less than Max limit
            {
                robot.motorArm.setPower(gamepad1.right_trigger / 2.0); //let trigger run +motor
            }

            else
            {
                robot.motorArm.setPower(0.0); // else not trigger, then set to off or some value of 'hold' power
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
                robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
