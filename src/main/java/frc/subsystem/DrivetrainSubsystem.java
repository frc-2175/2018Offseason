package frc.subsystem;

import frc.MotorWrapper;
import frc.ServiceLocator;
import frc.VirtualSpeedController;
import frc.info.RobotInfo;
import frc.info.SmartDashboardInfo;
import frc.SolenoidWrapper;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class DrivetrainSubsystem extends BaseSubsystem {
	private final RobotInfo robotInfo;
	private final SmartDashboardInfo smartDashboardInfo;
	private final MotorWrapper leftMaster;
	private final MotorWrapper leftSlaveOne;
	private final MotorWrapper leftSlaveTwo;
	private final MotorWrapper rightMaster;
	private final MotorWrapper rightSlaveOne;
	private final MotorWrapper rightSlaveTwo;
	private final SolenoidWrapper driveShifters;
	private final DifferentialDrive robotDrive;
	private static VirtualSpeedController leftVirtualSpeedController = new VirtualSpeedController();
	private static VirtualSpeedController rightVirtualSpeedController = new VirtualSpeedController();
	private static DifferentialDrive virtualRobotDrive = new DifferentialDrive(leftVirtualSpeedController,
		rightVirtualSpeedController);

	public static final double WIDTH_OF_BOT = 26.125;
	public static final double LENGTH_OF_BOT_WITH_BUMPERS = 39.25;

	private static final double INCHES_PER_TICK = (Math.PI * 6.25) / (15.32 * 1024) / 2;

	private boolean justTurned;

	// private AHRS navx;

	public DrivetrainSubsystem() {
		robotInfo = ServiceLocator.get(RobotInfo.class);
		leftMaster = robotInfo.get(RobotInfo.LEFT_MOTOR_MASTER);
		leftSlaveOne = robotInfo.get(RobotInfo.LEFT_MOTOR_SLAVE1);
		leftSlaveTwo = robotInfo.get(RobotInfo.LEFT_MOTOR_SLAVE2);
		rightMaster = robotInfo.get(RobotInfo.RIGHT_MOTOR_MASTER);
		rightSlaveOne = robotInfo.get(RobotInfo.RIGHT_MOTOR_SLAVE1);
		rightSlaveTwo = robotInfo.get(RobotInfo.RIGHT_MOTOR_SLAVE2);
		driveShifters = robotInfo.get(RobotInfo.DRIVE_SHIFTERS);

		leftSlaveOne.follow(leftMaster);
		leftSlaveTwo.follow(leftMaster);
		rightSlaveOne.follow(rightMaster);
		rightSlaveTwo.follow(rightMaster);

		robotDrive = new DifferentialDrive(leftMaster.getMotor(), rightMaster.getMotor());

		leftVirtualSpeedController = new VirtualSpeedController();
		rightVirtualSpeedController = new VirtualSpeedController();
		virtualRobotDrive = new DifferentialDrive(leftVirtualSpeedController, rightVirtualSpeedController);

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);

		justTurned = false;

		/* navx = new AHRS(SPI.Port.kMXP);
		navx.reset(); */

		smartDashboardInfo = ServiceLocator.get(SmartDashboardInfo.class);

		CameraServer.getInstance().startAutomaticCapture();
	}

	public void stopAllMotors() {
		blendedDrive(0, 0);
	}

	@Override
	public void periodic() {
	}

	/**
	 * Returns the lerp of the arcade and curvature values for the left and right
	 * virtual speed controllers
	 *
	 * @return {left, right}
	 */
	public static double[] getBlendedMotorValues(double moveValue, double turnValue) {
		SmartDashboardInfo smartDashboardInfo = ServiceLocator.get(SmartDashboardInfo.class);
		final double INPUT_THRESHOLD = 0.1;
		virtualRobotDrive.arcadeDrive(moveValue, turnValue, false);
		double leftArcadeValue = leftVirtualSpeedController.get()
			* smartDashboardInfo.getNumber(SmartDashboardInfo.TURN_IN_PLACE);
		double rightArcadeValue = rightVirtualSpeedController.get()
			* smartDashboardInfo.getNumber(SmartDashboardInfo.TURN_IN_PLACE);

		virtualRobotDrive.curvatureDrive(moveValue, turnValue, false);
		double leftCurvatureValue = leftVirtualSpeedController.get();
		double rightCurvatureValue = rightVirtualSpeedController.get();

		double lerpT = Math.abs(deadband(moveValue, RobotDriveBase.kDefaultDeadband)) / INPUT_THRESHOLD;
		lerpT = clamp(lerpT, 0, 1);
		double leftBlend = lerp(leftArcadeValue, leftCurvatureValue, lerpT);
		double rightBlend = lerp(rightArcadeValue, rightCurvatureValue, lerpT);

		double[] blends = { leftBlend, -rightBlend };
		return blends;
	}

	public void blendedDrive(double xSpeed, double zRotation) {
		double[] blendedValues = getBlendedMotorValues(-xSpeed, -zRotation);
		robotDrive.tankDrive(blendedValues[0], blendedValues[1]);
	}

	/**
	 * Clamps a double value based on a minimum and a maximum
	 *
	 * @param val the value to clamp
	 * @param min the minimum to clamp on
	 * @param max the maximum to clamp on
	 * @return min if val is less than min or max if val is greater than max
	 */
	public static double clamp(double val, double min, double max) {
		return val >= min && val <= max ? val : (val < min ? min : max);
	}

	/**
	 * Linearly interpolates between two points based on a t value
	 *
	 * @param a the point to interpolate from
	 * @param b the point to interpolate to
	 * @param t the value to interpolate on
	 * @return an output based on the formula lerp(a, b, t) = (1-t)a + tb
	 */
	public static double lerp(double a, double b, double t) {
		return (1 - t) * a + t * b;
	}

	// Copied from RobotDriveBase
	public static double deadband(double value, double deadband) {
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

	public static double undeadband(double value, double deadband) {
		if (value < 0) {
			double t = -value;
			return DrivetrainSubsystem.lerp(-deadband, -1, t);
		} else if (value > 0) {
			double t = value;
			return DrivetrainSubsystem.lerp(deadband, 1, t);
		} else {
			return 0;
		}
	}

	public double getLeftEncoderDistance() {
		return -leftMaster.getSelectedSensorPosition(0) * INCHES_PER_TICK;
	}

	public double getRightEncoderDistance() {
		return rightMaster.getSelectedSensorPosition(0) * INCHES_PER_TICK;
	}

	public double getAverageDistance() {
		return (getRightEncoderDistance() + getLeftEncoderDistance()) / 2;
	}

	/**
	 * Positive values = clockwise
	 */
	/*
	public double getGyroValueAdjusted() {
		double latency = smartDashboardInfo.getNumber(SmartDashboardInfo.GYRO_LATENCY);
		return navx.getAngle() + latency * navx.getRate() * navx.getActualUpdateRate();
	}

	public double getGyroValueUnadjusted() {
		return navx.getAngle();
	}
	*/

	public void resetGyro() {
		// navx.reset();
	}

	public void shift(boolean isHigh) {
		driveShifters.set(isHigh);
	}

	public void resetAllSensors() {
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
		resetGyro();
	}


	public void arcadeDrive(double moveValue, double turnValue) {
		robotDrive.arcadeDrive(-moveValue, -turnValue);
	}

	/* public void straightArcadeDrive(double moveValue) {
		double turnCorrection = smartDashboardInfo.getNumber(SmartDashboardInfo.TURN_CORRECTION);
		arcadeDrive(moveValue, -(getGyroValueUnadjusted() / turnCorrection));
	}

	public void straightArcadeDrive(double moveValue, boolean useTurnCorrection) {
		double turnCorrection = smartDashboardInfo.getNumber(SmartDashboardInfo.TURN_CORRECTION);
		arcadeDrive(moveValue, useTurnCorrection ? -(getGyroValueUnadjusted() / turnCorrection) : 0);
	} */

	public void tankDrive(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(-leftSpeed, -rightSpeed);
	}

	public void turned(boolean isTrue) {
		justTurned = isTrue;
	}

	public void gyroCorrectAssumptionDrive(double moveValue, double initTime, boolean useTurnCorrection) {
		boolean usingCorrection = true;
		if (initTime > 0.2 && justTurned) {
			resetGyro();
			justTurned = false;
		} else if (justTurned) {
			usingCorrection = false;
		}
		// straightArcadeDrive(moveValue, usingCorrection && useTurnCorrection);
	}
}
