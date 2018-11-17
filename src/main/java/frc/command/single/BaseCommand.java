package frc.command.single;

import edu.wpi.first.wpilibj.command.Command;

public abstract class BaseCommand extends Command {
	public BaseCommand() {
	}

	@Override
	protected void initialize() {
		init();
	}

	protected void init() {
	}

	@Override
	protected void end() {
		onEnd();
	}

	protected void onEnd() {
	}

	@Override
	protected void interrupted() {
		whenInterrupted();
		end();
	}

	protected void whenInterrupted() {
		onEnd();
	}

	protected boolean timeGreatEnough() {
		return timeSinceInitialized() > .3;
	}

	protected double abs(double val) {
		return Math.abs(val);
	}
}
