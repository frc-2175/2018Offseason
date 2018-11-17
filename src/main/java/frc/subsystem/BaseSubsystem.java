package frc.subsystem;

import frc.ServiceLocator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BaseSubsystem extends Subsystem {
	public BaseSubsystem() {
		ServiceLocator.register(this);
	}

	@Override
	public void setDefaultCommand(final Command command) {
		super.setDefaultCommand(command);
	}

	@Override
	protected void initDefaultCommand() {
	}
}
