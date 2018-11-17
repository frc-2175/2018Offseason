package frc.control;

import frc.ServiceLocator;

public class JoystickEventMapper {

	public JoystickEventMapper() {
		DryverStation driverStation = ServiceLocator.get(DryverStation.class);
		driverStation.getShiftButton().whileHeld(new ShiftCommand());
	}
}
