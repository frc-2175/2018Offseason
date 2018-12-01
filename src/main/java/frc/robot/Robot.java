/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/* Made by friends of 
______   __    ______   _______ 
/ ___  \ /  \  / ___  \ (  __   )
\/   \  \\/) ) \/   \  \| (  )  |
   ___) /  | |    ___) /| | /   |
  (___ (   | |   (___ ( | (/ /) |
      ) \  | |       ) \|   / | |
/\___/  /__) (_/\___/  /|  (__) |
\______/ \____/\______/ (_______)

*/
                                 

package frc.robot;

import frc.ServiceLocator;
import frc.command.DefaultCommandFactory;
import frc.control.DryverStation;
import frc.control.JoystickEventMapper;
import frc.factory.InfoFactory;
import frc.factory.SubsystemsFactory;
import frc.subsystem.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autoSelected;
	private DrivetrainSubsystem drivetrainSubsystem;

	@Override
	public void robotInit() {
		ServiceLocator.register(this);
		InfoFactory.makeAllInfos();
		new DryverStation();
		SubsystemsFactory.makeAllSubsystems();
		drivetrainSubsystem = ServiceLocator.get(DrivetrainSubsystem.class);
		new JoystickEventMapper();
		new DefaultCommandFactory();

	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("LeftEncoder", drivetrainSubsystem.getLeftEncoderDistance());
		SmartDashboard.putNumber("RightEncoder", drivetrainSubsystem.getRightEncoderDistance());
		/* SmartDashboard.putNumber("gyroUnadjusted", drivetrainSubsystem.getGyroValueUnadjusted());
		SmartDashboard.putNumber("gyroAdjusted", drivetrainSubsystem.getGyroValueAdjusted()); */
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		drivetrainSubsystem.resetAllSensors();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		drivetrainSubsystem.resetAllSensors();
		if (m_autoSelected != null) {
			m_autoSelected.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}
}
