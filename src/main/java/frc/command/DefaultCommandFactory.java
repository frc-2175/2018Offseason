package frc.command;

import frc.ServiceLocator;
import frc.command.single.BlendedDriveDefaultCommand;
import frc.subsystem.DrivetrainSubsystem;

public class DefaultCommandFactory {
	private final DrivetrainSubsystem drivetrainSubsystem;

	public DefaultCommandFactory() {
		drivetrainSubsystem = ServiceLocator.get(DrivetrainSubsystem.class);
		constructDefaultCommands();
	}

	private void constructDefaultCommands() {
		drivetrainSubsystem.setDefaultCommand(new BlendedDriveDefaultCommand());
	}

}
