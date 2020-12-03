/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallIntake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.BallIntake;

public class BallIntakeDown extends CommandBase {
  private BallIntake m_ballIntake;
  private boolean m_finished = false;
   
  /**
   * Creates a new BallIntakeDown.
   */
   public BallIntakeDown() {
    m_ballIntake = BallIntake.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(BallIntake.getInstance());
  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    BallIntakeDown cmd = new BallIntakeDown();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Basic", cmd);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { 
    m_ballIntake.lowerIntake();
    m_finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
