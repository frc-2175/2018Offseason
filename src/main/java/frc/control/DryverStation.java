package frc.control;

import frc.ServiceLocator;
import frc.info.RobotInfo;
import frc.info.SmartDashboardInfo;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class DryverStation {
	private Joystick leftJoystick;
	private Joystick rightJoystick;
	private Joystick gamepad;
	private SmartDashboardInfo smartDashboardInfo;
	private static final double JOYSTICK_DEADBAND = 0.15;
	private static final double GAMEPAD_DEADBAND = 0.1;

	public DryverStation() {
		ServiceLocator.register(this);
		RobotInfo robotInfo = ServiceLocator.get(RobotInfo.class);

		leftJoystick = robotInfo.get(RobotInfo.LEFT_JOYSTICK);
		rightJoystick = robotInfo.get(RobotInfo.RIGHT_JOYSTICK);
		gamepad = robotInfo.get(RobotInfo.GAMEPAD);

		smartDashboardInfo = ServiceLocator.get(SmartDashboardInfo.class);

		shiftButton = new JoystickButton(leftJoystick, 1);
	}

	public double getMoveValue() {
		return deadband(-leftJoystick.getY(), JOYSTICK_DEADBAND);
	}

	public double getTurnValue() {
		double value = Math.asin(rightJoystick.getX()) * 0.8;
		return deadband(value, JOYSTICK_DEADBAND);
	}

	protected double deadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

	public JoystickButton getShiftButton() {
		return shiftButton;
	}
}
