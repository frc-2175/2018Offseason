package frc.command.single;

import frc.ServiceLocator;
import frc.control.DryverStation;
import frc.subsystem.DrivetrainSubsystem;

public class BlendedDriveDefaultCommand extends BaseCommand {
	private final DrivetrainSubsystem drivetrainSubsystem;
	private final DryverStation driverStation;

	public BlendedDriveDefaultCommand() {
		drivetrainSubsystem = ServiceLocator.get(DrivetrainSubsystem.class);
		driverStation = ServiceLocator.get(DryverStation.class);
		requires(drivetrainSubsystem);
	}

	@Override
	protected void execute() {
		drivetrainSubsystem.blendedDrive(driverStation.getMoveValue(), driverStation.getTurnValue());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void onEnd() {
		drivetrainSubsystem.stopAllMotors();
	}
}
