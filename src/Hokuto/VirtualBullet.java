package Hokuto;

import Hokuto.Enemy.Enemy;
import java.awt.Graphics2D;
import robocode.Robot;
import robocode.Rules;

public class VirtualBullet {
    private double heading;
    private double x;
    private double y;
    private double power;
    private boolean active = true;
    private long firedTime;
    private long inactiveTime;
    private double firedX;
    private double firedY;

    public VirtualBullet(double heading, double x, double y, double power, long fireTime) {
	this.heading = heading;
	this.x = firedX = x;
	this.y = firedY = y;
	this.power = power;
	this.firedTime = fireTime;
    }
    public VirtualBullet(double heading, double x, double y, double power, boolean active, long fireTime) {
	this(heading, x, y, power, fireTime);
	this.active = active;
    }

    public double getHeading() {
	return heading;
    }
    public double getX() {
	return x;
    }
    public double getY() {
	return y;
    }
    public double getPower() {
	return power;
    }
    public boolean isActive() {
	return active;
    }
    public long getFiredTime() {
	return this.firedTime;
    }
    public long getInactiveTime() {
	return this.inactiveTime;
    }
    public double getFiredX() {
	return this.firedX;
    }
    public double getFiredY() {
	return this.firedY;
    }
    
    public double getVelocity() {
	return Rules.getBulletSpeed(this.power);
    }
    public double getDamage() {
	return Rules.getBulletDamage(this.power);
    }
    public double getGunHeat() {
	return Rules.getGunHeat(this.power);
    }
    
    public boolean hitEnemy(Enemy e) {
	return this.getX() > e.getX()-18
	    && this.getX() < e.getX()+18
	    && this.getY() > e.getY()-18
	    && this.getY() < e.getY()+18;
    }
    public boolean isOut(Robot r) {
	return this.getX() > r.getBattleFieldWidth()
	    || this.getX() < 0
	    || this.getY() > r.getBattleFieldHeight()
	    || this.getY() < 0;
    }

    public void setHeading(double heading) {
	this.heading = heading;
    }
    public void setX(double x) {
	this.x = x;
    }
    public void setY(double y) {
	this.y = y;
    }
    public void setPower(double power) {
	this.power = power;
    }
    public void setActive(boolean active) {
	this.active = active;
    }
    public void setInactiveTime(long time) {
	this.inactiveTime = time;
    }
    
    public void move() {
	this.setX(this.getX() + Math.sin(this.getHeading()) * this.getVelocity() * 1);
	this.setY(this.getY() + Math.cos(this.getHeading()) * this.getVelocity() * 1);
    }
    
    public void paint(Graphics2D g) {
	g.fillOval((int)this.getX()-3, (int)this.getY()-3, 6, 6);
    }
}