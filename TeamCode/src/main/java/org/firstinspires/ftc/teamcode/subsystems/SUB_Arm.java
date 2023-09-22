package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants.ArmConstants;

import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class SUB_Arm extends SubsystemBase {

    OpMode m_opMode;
    private Servo m_armservo;
    public ElapsedTime m_runTime = new ElapsedTime();

    public SUB_Arm(OpMode p_opMode, final String p_grabberservo) {
        m_opMode = p_opMode;
        m_armservo = m_opMode.hardwareMap.servo.get(p_grabberservo);

//        PwmControl.PwmRange range = new PwmControl.PwmRange(500, 2500);
//        m_pivotservo.setPwmRange(range);
    }

    public void setOpen(){
        m_armservo.setPosition(1);
    }

    public void setClosed(){
        m_armservo.setPosition(0);
    }

     public void periodic() {
//        telemetry();
     }

}
