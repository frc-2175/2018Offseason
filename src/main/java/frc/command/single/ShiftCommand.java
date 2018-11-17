package frc.command.single;

import frc.ServiceLocator;
import frc.subsystem.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftCommand extends Command {
	private final DrivetrainSubsystem drivetrainSubsystem;

	public ShiftCommand() {
		drivetrainSubsystem = ServiceLocator.get(DrivetrainSubsystem.class);
	}

	@Override
	protected void initialize() {
		drivetrainSubsystem.shift(true);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrainSubsystem.shift(false);
	}

}
