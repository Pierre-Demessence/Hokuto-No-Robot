package Hokuto.Part;

import Hokuto.Part.Fire.BasicFirePower;
import Hokuto.Part.Fire.FirePower;
import Hokuto.Hokuto_no_Robot;
import Hokuto.Part.Targeting.CircularTargeting;
import Hokuto.Part.Targeting.HeadOnTargeting;
import Hokuto.Part.Targeting.LinearTargeting;
import Hokuto.Part.Targeting.Targeting;
import Hokuto.VirtualBullet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import robocode.Bullet;
import robocode.util.Utils;

public class Gun extends Part {
    
    private ArrayList<Targeting> targetingList;
    private ArrayList<FirePower> firePowerList;
    private Targeting currentTargeting;
    private FirePower currentFirePower;
    
    private ArrayList<Bullet> bullets;

    public Gun(Hokuto_no_Robot robot) {
	super(robot);
	this.bullets = new ArrayList<>(0);
	
	this.targetingList = new ArrayList<>(0);
	this.currentTargeting = null;
	this.targetingList.add(new HeadOnTargeting(this));
	this.targetingList.add(new LinearTargeting(this));
	this.targetingList.add(new CircularTargeting(this));
	
	this.firePowerList = new ArrayList<>(0);
	this.currentFirePower = null;
	this.firePowerList.add(new BasicFirePower(this));
    }

    public String getCurrentTargetingName() {
	return (this.currentTargeting!=null ? this.currentTargeting.getClass().getSimpleName() : "None");
    }
    public String getCurrentFirepowerName() {
	return (this.currentFirePower!=null ? this.currentFirePower.getClass().getSimpleName() : "None");
    }
    public void setIndependantFromRobot(boolean b) {
	robot.setAdjustGunForRobotTurn(b);
    }
    public boolean isIndependantFromRobot() {
	return robot.isAdjustGunForRobotTurn();
    }
    
    public double getFirePower() {
	return this.currentFirePower.getFirePower();
    }
    public Targeting getMoreAccurate() {
	Targeting t = this.targetingList.get(0);
	for (int i=1 ; i<this.targetingList.size() ; i++) {
	    if(t.getAcuracy() < this.targetingList.get(i).getAcuracy())
		t = this.targetingList.get(i);
	}
	return t;
    }
    public int getTotalAccuracy() {
	int res = 0;
	for(Targeting t : this.targetingList) {
	    res += t.getAcuracy();
	}
	return res;
    }
    private void sortByAccuracy() {
	ArrayList<Targeting> res = new ArrayList<>(0);
	ArrayList<Targeting> old = new ArrayList<>(this.targetingList);
	
	int sizeInit = old.size();
	
	for(int i=0 ; i<sizeInit ; i++) {
	    
	    int size = old.size();
	    Targeting t = old.get(0);
	    for(int j=1 ; j<size ; j++) {
		if(t.getAcuracy() > old.get(j).getAcuracy())
		    t = old.get(j);
	    }
	    
	    res.add(t);
	    old.remove(t);
	}
	this.targetingList = res;
    }

    public void fire() {
	this.currentFirePower = this.firePowerList.get(0);
	if(this.robot.hasTarget() && this.robot.getGunTurnRemainingRadians() < Math.PI/180) {
	    double firePower = this.currentFirePower.getFirePower();
	    
	    if(this.robot.getGunHeat()==0.0)
		this.bullets.add(this.robot.setFireBullet(firePower));
	    
	    for(Targeting t : this.targetingList) {
		double heading = Hokuto.Utils.absoluteBearing(this.getX(), this.getY(), t.getX(), t.getY());
		t.addVirtualBullet(new VirtualBullet(heading, this.getX(), this.getY(), this.getFirePower(), this.robot.getTime()));
	    }
	}
    }

    public void aim() {
	this.currentTargeting = getMoreAccurate();
	
	if(this.robot.hasTarget()) {
	    double heading = Hokuto.Utils.absoluteBearing(this.getX(), this.getY(), this.currentTargeting.getX(), this.currentTargeting.getY()) - this.getGunHeading();
	    this.robot.setTurnGunRightRadians(Utils.normalRelativeAngle(heading));
	}
    }
    
    public void simulate() {
	for(Targeting t : this.targetingList) {
	    for(VirtualBullet b : t.getVirtualBullets()) {
		if(b.isActive()) {
		    b.move();
		    if(this.robot.hasTarget() && b.hitEnemy(this.getTarget())) {
			t.hit();
			b.setActive(false);
			b.setInactiveTime(this.robot.getTime());
		    }
		    else if(b.isOut(robot)) {
			b.setActive(false);
			b.setInactiveTime(this.robot.getTime());
		    }
		}
	    }
	}
    }
    
    public void paint(Graphics2D g) {
	int i = 0;
	for(Targeting t : this.targetingList) {
	    
	    if(this.robot.hasTarget()) {
		if(t.equals(this.currentTargeting))
		    g.setColor(Color.BLUE);
		else
		    g.setColor(Color.CYAN);
		g.drawLine((int)this.getX(), (int)this.getY(), (int)t.getX(), (int)t.getY());
		g.drawRect((int)t.getX()-18, (int)t.getY()-18, 36, 36);
	    }

	    for(VirtualBullet b : t.getVirtualBullets()) {
		if(b.isActive()) {
		    if(t.equals(this.currentTargeting))
			g.setColor(Color.DARK_GRAY);
		    else
			g.setColor(Color.GRAY);
		    b.paint(g);
		}
	    }
	    
	    g.setColor(Color.GREEN);
	    this.sortByAccuracy();
	    g.drawString(t.getClass().getSimpleName()+" Accuracy : "+Hokuto.Utils.round(t.getAcuracy(), 1)+"%", 5, 70+12*i++);
	}
    }

}