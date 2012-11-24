package Hokuto.Enemy;

import Hokuto.VirtualBullet;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Enemy {
    
    private String name;
    private LinkedList<Long> time;
    private LinkedList<Double> energy;
    private LinkedList<Double> velocity;
    private LinkedList<Double> heading;
    private LinkedList<Double> bearing;
    private LinkedList<Double> distance;
    private LinkedList<Double> x;
    private LinkedList<Double> y;
    private ArrayList<VirtualBullet> bullets;
    private boolean fired;
    
    public Enemy() {
	this.reset();
    }
    public Enemy(ScannedRobotEvent e, AdvancedRobot robot) {
	this();
	this.update(e, robot);
    }
    
    public String getName() {
	return name;
    }
    public long getTime() {
	return time.getLast();
    }
    private long getTimeReverse(int i) {
	    return (i > this.time.size() ? this.time.get(this.time.size()-(1+i)) : 0);
    }
    public double getEnergy() {
	return energy.getLast();
    }
    public double getVelocity() {
	return velocity.getLast();
    }
    public double getHeading() {
	return heading.getLast();
    }
    private double getHeadingReverse(int i) {
	return (i > this.heading.size() ? this.heading.get(this.heading.size()-(1+i)) : 0);
    }
    public double getBearing() {
	return bearing.getLast();
    }
    public double getDistance() {
	return distance.getLast();
    }
    public double getX() {
	return x.getLast();
    }
    public double getY() {
	return y.getLast();
    }
    public boolean hasFired() {
	return this.fired;
    }
    public ArrayList<VirtualBullet> getBullets() {
	return this.bullets;
    }
    
    public double getTurnRate() {
	return (this.heading.size() > 1 ? (this.getHeading()-this.getHeadingReverse(1))/(this.getTime()-this.getTimeReverse(1)) : 0);
    }
    
    public void update(ScannedRobotEvent e, AdvancedRobot r) {
	this.name = e.getName();
	this.time.addLast(e.getTime());
	this.energy.addLast(e.getEnergy());
        this.velocity.addLast(e.getVelocity());
        this.heading.addLast(e.getHeadingRadians());
	this.bearing.addLast(e.getBearingRadians());
        this.distance.addLast(e.getDistance());
	
	double energyLoss = (this.energy.size()>1 ? this.energy.get(this.energy.size()-2)-this.energy.getLast() : 0);
	if(energyLoss <= 3.0 && energyLoss >= 0.1) {
	    this.fired = true;
	    this.bullets.add(new VirtualBullet(Utils.normalRelativeAngle(r.getHeadingRadians()+this.getBearing()+Math.PI), this.getX(), this.getY(), energyLoss, r.getTime()));
	} else
	    this.fired = false;
	
	for(VirtualBullet b : this.bullets) {
	    if(b.isActive())
		b.move();
	}
        
	double absBearingRad = r.getHeadingRadians() + e.getBearingRadians();
        while (absBearingRad < 0) absBearingRad += 2*Math.PI;
        this.x.addLast(r.getX() + Math.sin(absBearingRad) * e.getDistance());
        this.y.addLast(r.getY() + Math.cos(absBearingRad) * e.getDistance());
	
	this.deleteOld();
    }
    
    private void deleteOld() {
	final int max = 10;
	
	if(this.time.size()>max)
	    this.time.removeFirst();
	if(this.energy.size()>max)
	    this.energy.removeFirst();
	if(this.velocity.size()>max)
	    this.velocity.removeFirst();
	if(this.heading.size()>max)
	    this.heading.removeFirst();
	if(this.bearing.size()>max)
	    this.bearing.removeFirst();
	if(this.distance.size()>max)
	    this.distance.removeFirst();
	if(this.x.size()>max)
	    this.x.removeFirst();
	if(this.y.size()>max)
	    this.y.removeFirst();
    }
    
    private void reset() {
        this.name = "";
	this.time = new LinkedList<>();
        this.energy  = new LinkedList<>();
        this.velocity  = new LinkedList<>();
        this.heading  = new LinkedList<>();
        this.bearing  = new LinkedList<>();
        this.distance  = new LinkedList<>();
	this.x  = new LinkedList<>();
        this.y  = new LinkedList<>();
	this.bullets = new ArrayList<>(0);
	this.fired = false;
    }
    
    public void paint(Graphics2D g, long time) {
	for(VirtualBullet b : this.bullets) {
	    if(b.isActive()) {
		double radius = b.getVelocity() * (time - b.getFiredTime()+1);
		g.drawOval((int)(b.getFiredX()-radius), (int)(b.getFiredY()-radius), (int)radius*2, (int)radius*2);
		g.drawLine((int)b.getFiredX(), (int)b.getFiredY(), (int)b.getX(), (int)b.getY());
		b.paint(g);
		if(radius > this.getDistance() + 30)
		    b.setActive(false);
	    }
	}
    }
}