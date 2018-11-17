package frc.subsystem;

import frc.ServiceLocator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BaseSubsystem extends Subsystem {
	private final Logger log = RobotLogger.getLogger(getClass());

	public BaseSubsystem() {
		log.info("Constructing " + getClass().getName());
		ServiceLocator.register(this);
	}

	@Override
	public void setDefaultCommand(final Command command) {
		log.info("Setting default command for " + getClass().getName() + " to " + command.getName());
		super.setDefaultCommand(command);
	}

	@Override
	protected void initDefaultCommand() {
	}
}
