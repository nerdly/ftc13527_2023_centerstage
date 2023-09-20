package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
// import org.firstinspires.ftc.teamcode.ftclib.hardware.motors.Motor.Encoder;
// import org.firstinspires.ftc.teamcode.ftclib.hardware.motors.MotorEx;
 import org.firstinspires.ftc.teamcode.ftclib.hardware.motors.Motor.ZeroPowerBehavior;

public class SUB_Elevator extends SubsystemBase {

    OpMode m_opMode;
    private DcMotor m_elevatormotor1;
    private DcMotor m_elevatormotor2;
    private Servo m_elevatorservo;


    public int m_level = 0;// 0 = home  1 = low-goal  2 = mid-goal  3 = high-goal
    int m_wantedPosition;
    int m_tolerance = 15;
    int m_homeLevel = 0;
    int m_lowLevel = 1;
    int m_midLevel = 2;
    int m_highLevel = 3;
    double m_elevatorPower = 1;

    public SUB_Elevator(OpMode p_opMode, final String p_elevatormotor1, final String p_elevatormotor2,
                        final String p_elevatorservo) {
        m_opMode = p_opMode;
        m_elevatormotor1 = m_opMode.hardwareMap.dcMotor.get(p_elevatormotor1);
        m_elevatormotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_elevatormotor2 = m_opMode.hardwareMap.dcMotor.get(p_elevatormotor2);
        m_elevatormotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveToLevelByID(int p_levelID){
        m_level = p_levelID;
        switch (m_level) {
            case ElevatorConstants.k_homeLevelID :  // home
                m_wantedPosition = ElevatorConstants.k_homeLevelEncoderPosition;
                break;
            case ElevatorConstants.k_lowLevelID : // low
                m_wantedPosition = ElevatorConstants.k_lowLevelEncoderPosition;
                break;
            case ElevatorConstants.k_midLevelID : // mid
                m_wantedPosition = ElevatorConstants.k_midLevelEncoderPosition;
                break;
            case ElevatorConstants.k_highLevelID : // high
                m_wantedPosition = ElevatorConstants.k_highLevelEncoderPosition;
                break;
            case ElevatorConstants.k_intakeLevelID : // intake
                m_wantedPosition = ElevatorConstants.k_intakeLevelEncoderPosition;
                break;
            case ElevatorConstants.k_groundLevelID : // intake
                m_wantedPosition = ElevatorConstants.k_groundLevelEncoderPosition;
                break;

        }

        m_elevatormotor.setTargetPosition(m_wantedPosition);
        m_elevatormotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_elevatormotor.setPower(m_elevatorPower);

    }

    public void telemetry() {
        m_opMode.telemetry.addData("Elevator "," [level:%d] %d",m_level, m_elevatormotor.getCurrentPosition());
    }

    public int getEncoderTicks(){
        return m_elevatormotor.getCurrentPosition();
    }

    public void ResetEncoder(){
        m_elevatormotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int getLevel() {
        return m_level;
    }

    public boolean atPosition(){
        return (Math.abs(getEncoderTicks() - m_wantedPosition) < m_tolerance);
    }

    public void telemetry(){

    }

    public void periodic() {
        telemetry();
    }

}