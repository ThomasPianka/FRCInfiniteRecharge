/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDBottomShooter extends PIDCommand {
  /**
   * Creates a new PIDBottomShooter.
   */
  public PIDBottomShooter(double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(Shooter.getInstance().getkP(), Shooter.getInstance().getkI(), Shooter.getInstance().getkD()),
        // This should return the measurement
        () -> Shooter.getInstance().getRPM(Shooter.getInstance().getBottomEncoder()),
        // This should return the setpoint (can also be a constant)
        () -> setpoint,
        // This uses the output
        output -> {
          // Use the output here
          Shooter.getInstance().setBottom(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(10);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double setpoint = TestingDashboard.getInstance().getNumber(shooter, "Bottom Setpoint");
    PIDBottomShooter cmd = new PIDBottomShooter(setpoint);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(shooter, "PIDController", "BottomPIDController", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //getController().atSetpoint();
  }
}
