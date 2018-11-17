package frc.factory;

import frc.factory.SubsystemsFactory;
import frc.subsystem.DrivetrainSubsystem;

public class SubsystemsFactory {
	public static void makeAllSubsystems() {
		new DrivetrainSubsystem();
	}
}
