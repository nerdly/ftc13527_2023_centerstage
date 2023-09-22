package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.util.ElapsedTime;


public class AutoSequencer extends SubsystemBase {
//    private RobotContainer m_robot;
    private ElapsedTime m_runtime = new ElapsedTime();
    private boolean m_isRunning=false;
    private int m_stepNumber=0;


    public AutoSequencer() {
//        m_robot=p_robot
    }


    public void setIsRunning(boolean p_isRunning) {
        m_isRunning = p_isRunning;
    }

    public boolean getIsRunning() {
        return m_isRunning;
    }

    public void setStepNumber(int p_stepNumber){
        m_isRunning = true;
        m_stepNumber = p_stepNumber;
    }

    public int getStepNumber() {
        return m_stepNumber;
    }

    public void reset() {
        m_runtime.reset();
        m_stepNumber = 0;
        m_isRunning = false;
    }

    public double getRuntime() {
        return m_runtime.milliseconds();
    }

    @Override
    public void periodic() {

    }

}

