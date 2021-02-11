// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.TestingDashboard;

public class TurnAngle extends CommandBase {
  static Drive m_drive;
  // angle is in degrees
  double m_angle;
  double m_finalAngle;
  boolean m_parameterized;
  double m_speed;
  double m_initialAngle;
  // positive is clockwise, negative is counter-clockwise
  double m_direction;

  final double CLOCKWISE = 1;
  final double COUNTER_CLOCKWISE = -1;

  /** Creates a new TurnAngle. */
  public TurnAngle(double angle, double speed, boolean parameterized) {
    m_drive = Drive.getInstance();
    m_parameterized = parameterized;
    m_direction = angle/Math.abs(angle);
    m_angle = (Math.abs(angle) % 360) * m_direction;
    updateFinalAngle();
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TurnAngle cmd = new TurnAngle(12.0, Drive.INITIAL_SPEED, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_initialAngle = m_drive.getYaw();
    if (!m_parameterized) {
      TestingDashboard.getInstance().updateNumber(m_drive, "InitialAngle", m_initialAngle);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_angle = TestingDashboard.getInstance().getNumber(m_drive, "AngleToTurnInDegrees");
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
    }
    m_drive.tankDrive(m_speed * m_direction, m_speed * m_direction * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = false;
    double yaw = m_drive.getYaw();
    if (m_direction == CLOCKWISE) {
      if (yaw > m_finalAngle) {
        if (m_initialAngle > 0 && m_finalAngle < 0) {
          if (yaw < 0) {
            finished = true;
          }
        } else {
          finished = true;
        }
        
      }
    } else if (m_direction == COUNTER_CLOCKWISE) {
      if (yaw < m_finalAngle) {
        if (m_initialAngle < 0 && m_finalAngle > 0) {
          if (yaw > 0) {
            finished = true;
          }
        } else {
          finished = true;
        }
      }
    }

    return finished;
  }

  public void updateFinalAngle() {

    m_finalAngle = m_initialAngle + m_angle;
    if (m_finalAngle > 180) {
      double delta = m_angle - (180-m_initialAngle);
      m_finalAngle = -180 + delta;
    } else if (m_finalAngle < -180) {
      double delta = m_angle - (-180-m_initialAngle);
      m_finalAngle = 180 + delta;
    }

  }
}
