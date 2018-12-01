package frc.control;

import frc.ServiceLocator;
// import frc.command.single.ShiftCommand;

public class JoystickEventMapper {

	public JoystickEventMapper() {
		DryverStation driverStation = ServiceLocator.get(DryverStation.class);
		// driverStation.getShiftButton().whileHeld(new ShiftCommand());
	}
}
