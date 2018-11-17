package frc.info;

import frc.ServiceLocator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInfo {
	private static final String PREFIX = "AutoPopulate/";
	public static final String POSITIVE_DEADBAND = "deadband.positive";
	public static final String NEGATIVE_DEADBAND = "deadband.negative";
	public static final String TURN_CORRECTION = "turn.correction";
	public static final String DRIVE_CURVE_MAX_SPEED = "drivecurve.maxspeed";
	public static final String DRIVE_STRAIGHT_ACCELERATION_RATE = "drivestraight.acceleration";
	public static final String GYRO_LATENCY = "gyro.latency";
	public static final String TURN_IN_PLACE = "drivetraim.turninplace";

	public SmartDashboardInfo() {
		ServiceLocator.register(this);
		populate();
	}

	private void populate() {
		putNumber(POSITIVE_DEADBAND, 0.1);
		putNumber(NEGATIVE_DEADBAND, -0.05);
		putNumber(TURN_CORRECTION, 8);
		putNumber(DRIVE_CURVE_MAX_SPEED, 0.7);
		putNumber(DRIVE_STRAIGHT_ACCELERATION_RATE, 3);
		putNumber(GYRO_LATENCY, 0.2);
		putNumber(TURN_IN_PLACE, 0.8);
	}

	public void putBoolean(String key, boolean value) {
		SmartDashboard.putBoolean(PREFIX + key, value);
	}

	public void putNumber(String key, double value) {
		SmartDashboard.putNumber(PREFIX + key, value);
	}

	public void putString(String key, String value) {
		SmartDashboard.putString(PREFIX + key, value);
	}

	public void putBooleanArray(String key, boolean[] value) {
		SmartDashboard.putBooleanArray(PREFIX + key, value);
	}

	public void putNumberArray(String key, double[] value) {
		SmartDashboard.putNumberArray(PREFIX + key, value);
	}

	public void putStringArray(String key, String[] value) {
		SmartDashboard.putStringArray(PREFIX + key, value);
	}

	public void putRaw(String key, byte[] value) {
		SmartDashboard.putRaw(PREFIX + key, value);
	}

	public boolean getBoolean(String key) {
		return SmartDashboard.getBoolean(PREFIX + key, false);
	}

	public double getNumber(String key) {
		return SmartDashboard.getNumber(PREFIX + key, 0);
	}

	public String getString(String key) {
		return SmartDashboard.getString(PREFIX + key, "");
	}

	public boolean[] getBooleanArray(String key) {
		boolean[] defaultArray = { false };
		return SmartDashboard.getBooleanArray(PREFIX + key, defaultArray);
	}

	public double[] getNumberArray(String key) {
		double[] defaultArray = { 0 };
		return SmartDashboard.getNumberArray(PREFIX + key, defaultArray);
	}

	public String[] getStringArray(String key) {
		String[] defaultArray = { "" };
		return SmartDashboard.getStringArray(PREFIX + key, defaultArray);
	}

	public byte[] getRaw(String key) {
		byte[] defaultArray = { 0 };
		return SmartDashboard.getRaw(PREFIX + key, defaultArray);
	}
}
