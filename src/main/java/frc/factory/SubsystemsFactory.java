package frc.factory;

import frc.subsystem.DrivetrainSubsystem;

public class SubsystemsFactory {
	public static void makeAllSubsystems() {
		new DrivetrainSubsystem();
	}
}
