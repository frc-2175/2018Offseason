package frc.factory;

import frc.info.RobotInfo;
import frc.info.SmartDashboardInfo;

public class InfoFactory {
	public static void makeAllInfos() {
		new RobotInfo();
		new SmartDashboardInfo();
	}
}
