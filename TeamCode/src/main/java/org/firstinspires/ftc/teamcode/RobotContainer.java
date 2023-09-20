package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.ftclib.command.Command;
import org.firstinspires.ftc.teamcode.ftclib.command.Robot;
import org.firstinspires.ftc.teamcode.pipelines.Pipeline_PoleHunt;
import org.firstinspires.ftc.teamcode.subsystems.GlobalVariables;
import org.firstinspires.ftc.teamcode.subsystems.SUB_FourBar;
import org.firstinspires.ftc.teamcode.subsystems.SUB_IMU;
import org.firstinspires.ftc.teamcode.subsystems.SUB_LineSensor;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Arm;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Elevator;
import org.firstinspires.ftc.teamcode.subsystems.SUB_OpenCvCamera;
import org.firstinspires.ftc.teamcode.subsystems.SUB_FiniteStateMachine;
import org.firstinspires.ftc.teamcode.subsystems.SUB_sensor_ConeGuide;

public class RobotContainer {
    public boolean m_red = true;
    public Robot m_robot = new Robot();
    public MecanumDriveSubsystem drivetrain;
    public SUB_FiniteStateMachine finiteStateMachine;

    public SUB_OpenCvCamera frontCamera;
    public SUB_Arm m_arm;
    public SUB_IMU m_IMU;
    public SUB_Elevator m_elevator;
    public SUB_LineSensor lineSensor;
    public SUB_FourBar m_fourbar;

    public SUB_sensor_ConeGuide sensorConeGuide;
    public GlobalVariables GlobalVariables;

//    public Pipeline_PoleHunt m_poleHuntPipeline;

    public RobotContainer(OpMode p_opMode) {
        SampleMecanumDrive drivebase = new SampleMecanumDrive(p_opMode.hardwareMap);
        drivetrain = new MecanumDriveSubsystem(drivebase, true);

        finiteStateMachine = new SUB_FiniteStateMachine(p_opMode);
        frontCamera = new SUB_OpenCvCamera(p_opMode, "FrontCam");
        m_arm = new SUB_Arm(p_opMode, "grabberservo", "pivotservo");
        m_IMU = new SUB_IMU(p_opMode, "imu", "imu_2");
        m_elevator = new SUB_Elevator(p_opMode, "elevatormotor", m_IMU);
        lineSensor = new SUB_LineSensor(p_opMode, "irsensor_1", "irsensor_2");
        sensorConeGuide = new SUB_sensor_ConeGuide(p_opMode, "coneguide");
        GlobalVariables = new GlobalVariables();
        m_fourbar = new SUB_FourBar(p_opMode);
//        m_poleHuntPipeline = new Pipeline_PoleHunt();
    };

    public void run() {
        m_robot.run();
    }

    public void reset() {
        m_robot.reset();
    }

    public void schedule(Command... commands) {
            m_robot.schedule(commands);
    }
}

