package frc.info;

import java.util.HashMap;

import frc.MotorWrapper;
import frc.ServiceLocator;
import frc.SolenoidWrapper;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
// import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;

public class RobotInfo {
	public static interface ValueContainer {
		public Object get();
	}

	public static final String LEFT_MOTOR_MASTER = "drivetrain.motor.left.master";
	public static final String LEFT_MOTOR_SLAVE1 = "drivetrain.motor.left.slave1";
	public static final String LEFT_MOTOR_SLAVE2 = "drivetrain.motor.left.slave2";
	public static final String RIGHT_MOTOR_MASTER = "drivetrain.motor.right.master";
	public static final String RIGHT_MOTOR_SLAVE1 = "drivetrain.motor.right.slave1";
	public static final String RIGHT_MOTOR_SLAVE2 = "drivetrain.motor.right.slave2";
	public static final String LEFT_JOYSTICK = "driverstation.joystick.left";
	public static final String RIGHT_JOYSTICK = "driverstation.joystick.right";
	public static final String GAMEPAD = "driverstation.gamepad";
	public static final String DRIVE_SHIFTERS = "drive.shifters";

	private HashMap<String, Object> info;


	public RobotInfo() {
		ServiceLocator.register(this);
		info = new HashMap<>();
		populate();
	}

	private void populate() {
		put(LEFT_MOTOR_MASTER, talon(new WPI_TalonSRX(1)));
		put(LEFT_MOTOR_SLAVE1, victor(new WPI_VictorSPX(11)));
		put(LEFT_MOTOR_SLAVE2, victor(new WPI_VictorSPX(12)));
		put(RIGHT_MOTOR_MASTER, talon(new WPI_TalonSRX(4)));
		put(RIGHT_MOTOR_SLAVE1, victor(new WPI_VictorSPX(2)));
		put(RIGHT_MOTOR_SLAVE2, victor(new WPI_VictorSPX(3)));
		put(DRIVE_SHIFTERS, () -> new SolenoidWrapper(4));
		put(LEFT_JOYSTICK, new Joystick(0));
		put(RIGHT_JOYSTICK, new Joystick(1));
		put(GAMEPAD, new Joystick(2));
	}

	private void put(String key, ValueContainer value) {
		info.put(key, value);
	}

	private MotorWrapper talon(WPI_TalonSRX talon) {
		return new MotorWrapper(talon);
	}

	private MotorWrapper victor(WPI_VictorSPX victor) {
		return new MotorWrapper(victor);
	}

	private void put(String key, Object value) {
		info.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) info.get(key);
	}
}
