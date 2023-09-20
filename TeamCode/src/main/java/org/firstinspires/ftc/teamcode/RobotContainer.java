package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.ftclib.command.Command;
import org.firstinspires.ftc.teamcode.ftclib.command.Robot;
import org.firstinspires.ftc.teamcode.pipelines.*;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class RobotContainer {
    public boolean m_red = true;
    public Robot m_robot = new Robot();
    public MecanumDriveSubsystem drivetrain;

    public SUB_OpenCvCamera frontCamera;
    public SUB_Elevator m_elevator;
    public SUB_Arm m_arm;


    public RobotContainer(OpMode p_opMode) {
        SampleMecanumDrive drivebase = new SampleMecanumDrive(p_opMode.hardwareMap);
        drivetrain = new MecanumDriveSubsystem(drivebase, true);

        frontCamera = new SUB_OpenCvCamera(p_opMode, "FrontCam");
        m_elevator = new SUB_Elevator(p_opMode, "ElevatorMotor1", "ElevatorMotor2", "ElevatorServo");
        m_arm = new SUB_Arm(p_opMode, "ArmServo")
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

