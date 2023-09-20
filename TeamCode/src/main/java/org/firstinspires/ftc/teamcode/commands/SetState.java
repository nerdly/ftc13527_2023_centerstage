package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class SetState extends CommandBase {
    private final SUB_FiniteStateMachine m_finiteStateMachine;
    private final SUB_FiniteStateMachine.RobotState m_robotState;
    
    public SetState(SUB_FiniteStateMachine finiteStateMachine, SUB_FiniteStateMachine.RobotState robotState) {
        m_finiteStateMachine = finiteStateMachine;
        m_robotState = robotState;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_finiteStateMachine);
    }

    @Override
    public void initialize() {
        m_finiteStateMachine.setRobotState(m_robotState);
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
