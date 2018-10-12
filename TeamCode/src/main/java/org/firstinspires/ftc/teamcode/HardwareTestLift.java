package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;


/**
 * Created by TeameurekaRobotics on 12/30/2016
 *
 * This file contains an example Hardware Setup Class for a 1 motor and 1 servo.
 *
 */

public class HardwareTestLift {

   /* Declare Public OpMode members.
    *these are the null statements to make sure nothing is stored in the variables.
    */

    //Drive motors
        //Add Motors here
    public DcMotor motorFrontRight = null;
    public DcMotor motorFrontLeft = null;
    public DcMotor motorBackRight = null;
    public DcMotor motorBackLeft = null;

    //Accessories motors
    DcMotor motorArm = null;

    //servos
    Servo servoLatch = null;

    //sensors
        //Add sensors here
    //DigitalChannel sensorTouch = null;
/**
 * The REV Robotics Touch Sensor
 * is treated as a digital channel.  It is HIGH if the button is unpressed.
 * It pulls LOW if the button is pressed.
 *
 * Also, when you connect a REV Robotics Touch Sensor to the digital I/O port on the
 * Expansion Hub using a 4-wire JST cable, the second pin gets connected to the Touch Sensor.
 * The lower (first) pin stays unconnected.*
 */

    /* local OpMode members. */
    HardwareMap hwMap        = null;

    //Create and set default servo positions & MOTOR STOP variables.

    final static double MOTOR_STOP = 0.0; // sets motor power to zero
    final static double CLOSED = 0.2;
    final static double OPEN = 0.5;
    //CR servo variables
        //Add servo variable here

   /* Constructor   // this is not required as JAVA does it for you, but useful if you want to add
    * function to this method when called in OpModes.
    */
    public HardwareTestLift() {
    }

    //Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        /************************************************************
         * MOTOR SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File
        motorArm = hwMap.dcMotor.get("motorArm");
        motorFrontLeft = hwMap.dcMotor.get("motorFL");
        motorFrontRight = hwMap.dcMotor.get("motorFR");
        motorBackLeft = hwMap.dcMotor.get("motorBL");
        motorBackRight = hwMap.dcMotor.get("motorBR");

        // eg: Set the drive motor directions:
         // Can change based on motor configuration
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        // set Arm dirction and reset Encoder to zero
        motorArm.setDirection(DcMotor.Direction.FORWARD);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // runs motor faster than when set to RUN_USING_ENCODER
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Moter slower but more accurate

        /************************************************************
         * SERVO SECTION
         ************************************************************/
            //Add servo configuration
        servoLatch = hwMap.servo.get("servoLatch");

        /************************************************************
         * SENSOR SECTION
         ************************************************************/
            //Add sensors
        // get a reference to our sensorTouch object.
        //sensorTouch = hwMap.get(DigitalChannel.class, "limit");

        // set the digital channel to input.
        //sensorTouch.setMode(DigitalChannel.Mode.INPUT);

   }

}

