package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
  Joystick drv = new Joystick(0); 
  JoystickButton x = new JoystickButton(drv, 1);
  JoystickButton y = new JoystickButton(drv, 2);
  JoystickButton a = new JoystickButton(drv, 3);
  JoystickButton b = new JoystickButton(drv, 4);
  JoystickButton c = new JoystickButton(drv, 5);
  CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; 

  private void configureBindings() {
    x.and(() -> drv.getY() > 0).whileTrue(drivetrain.runDriveQuasiTest(Direction.kForward));
    x.and(() -> drv.getY() < 0).whileTrue(drivetrain.runDriveQuasiTest(Direction.kReverse));

    y.and(() -> drv.getY() > 0).whileTrue(drivetrain.runDriveDynamTest(Direction.kForward));
    y.and(() -> drv.getY() < 0).whileTrue(drivetrain.runDriveDynamTest(Direction.kReverse));

    a.and(() -> drv.getY() > 0).whileTrue(drivetrain.runSteerQuasiTest(Direction.kForward));
    a.and(() -> drv.getY() < 0).whileTrue(drivetrain.runSteerQuasiTest(Direction.kReverse));

    b.and(() -> drv.getY() > 0).whileTrue(drivetrain.runSteerDynamTest(Direction.kForward));
    b.and(() -> drv.getY() < 0).whileTrue(drivetrain.runSteerDynamTest(Direction.kReverse));
  
    c.and(() -> drv.getY() > 0).whileTrue(drivetrain.runDriveSlipTest());
  }

  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
      drivetrain.runDriveQuasiTest(Direction.kForward).withTimeout(5),
      drivetrain.runDriveQuasiTest(Direction.kReverse).withTimeout(5),
      drivetrain.runDriveDynamTest(Direction.kForward).withTimeout(5),
      drivetrain.runDriveDynamTest(Direction.kReverse).withTimeout(5));
  }
}
