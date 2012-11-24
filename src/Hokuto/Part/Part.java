package Hokuto.Part;

import Hokuto.Enemy.Enemy;
import Hokuto.Hokuto_no_Robot;
import robocode.Rules;

public abstract class Part {
    protected Hokuto_no_Robot robot;

    public Part(Hokuto_no_Robot robot) {
	this.robot = robot;
    }
    
    public double getHeading() {
	return this.robot.getHeadingRadians();
    }
    public Enemy getTarget() {
	return this.robot.getTarget();
    }
    public boolean hasTarget() {
	return this.robot.hasTarget();
    }
    public double getRadarHeading() {
	return this.robot.getRadarHeadingRadians();
    }
    public double getGunHeading() {
	return this.robot.getGunHeadingRadians();
    }
    public double getX() {
	return this.robot.getX();
    }
    public double getY() {
	return this.robot.getY();
    }
    public double getBattleFieldHeight() {
	return this.robot.getBattleFieldHeight();
    }
    public double getBattleFieldWidth() {
	return this.robot.getBattleFieldWidth();
    }
    public double getBulletSpeed(double bulletpower) {
	return Rules.getBulletSpeed(bulletpower);
    }
    public double getDistanceRemaining() {
	return this.robot.getDistanceRemaining();
    }
    public double getGunCoolingRate() {
	return this.robot.getGunCoolingRate();
    }
    
}