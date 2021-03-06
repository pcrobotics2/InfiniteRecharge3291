/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ColorWheel;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.DriverStation;


public class TurnToColor extends CommandBase {
  /**
   * Creates a new TurnToColor.
   */
  ColorWheel colorWheel;
  char targetColor;
  int targetPos;
  int startPos;
  int distance;
  int absDistance;
  Drivetrain m_drive;
  int time;
  boolean finished = false;


  public TurnToColor(ColorWheel m_ColorWheel, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    colorWheel = m_ColorWheel;
    m_drive = drive;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetColor = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
    time = 0;
    /*switch (targetColor){
      case 'B' :
      targetPos = Constants.bluePos;
      break;
    case 'G' :
      targetPos = Constants.greenPos;
      break;
    case 'R' :
      targetPos = Constants.redPos;
      break;
    case 'Y' :
      targetPos = Constants.yellowPos;
      break;
    default :
      cancel();
      break;
    }*/
    
    targetPos = Constants.greenPos;
    if(colorWheel.getColor() == 0){
      cancel();
    }else{
      startPos = colorWheel.getColor();
    }
    distance = targetPos - startPos;
    absDistance = Math.abs(distance);
    if(absDistance > Constants.numOfColors/2){
      distance = -(absDistance/distance)*(Constants.numOfColors - absDistance);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double error = colorWheel.moveNumberOfColors(distance);
    if(Math.abs(error) < 0.12){
      time++;
    }
    if(time >= 7){
      finished = true;
    }
    /*if(error < 0.10){
      cancel();
    }*/
    //m_drive.drive(-0.2, -0.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
