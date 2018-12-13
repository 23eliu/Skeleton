/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team852.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team852.Robot;
import frc.team852.RobotMap;
import frc.team852.lib.utilities.PIDController;


/**
 * An example command.  You can replace me with your own command.
 */
public class DistanceDrive extends Command {

    double left_power;
    double right_power;
    double left_initial;
    double right_initial;
    double left_distance;
    double right_distance;
    double left_target;
    double right_target;

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    PIDController pid = new PIDController(1,1,1);

    public DistanceDrive(double left_power, double right_power, double left_distance, double right_distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        this.left_power = left_power;
        this.right_power = right_power;
        this.left_distance = left_distance;
        this.right_distance = right_distance;

        rightEncoder = RobotMap.rightDriveEncoder;
        leftEncoder = RobotMap.leftDriveEncoder;
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        left_initial = leftEncoder.get();
        right_initial = rightEncoder.get();
        left_target = left_initial + left_distance;
        right_target = right_initial + right_distance;

    }

    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double left = pid.getPID(leftEncoder.get(), left_target);
        double right = pid.getPID(rightEncoder.get(), right_target);
        Robot.drivetrain.drive(left, right);

    }


    /**
     * <p>
     * Returns whether this command is finished. If it is, then the command will be removed and
     * {@link #end()} will be called.
     * </p><p>
     * It may be useful for a team to reference the {@link #isTimedOut()}
     * method for time-sensitive commands.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. It is recommended to use
     * {@link edu.wpi.first.wpilibj.command.InstantCommand} (added in 2017) for this.
     * </p>
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return (leftEncoder.get() - left_target <= 5) && (rightEncoder.get() - right_target <= 5);
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
