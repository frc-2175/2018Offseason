package frc.factory;

import frc.DrivetrainSubsystem;

public class SubsystemsFactory {
	public static void makeAllSubsystems() {
		new DrivetrainSubsystem();
	}
}
