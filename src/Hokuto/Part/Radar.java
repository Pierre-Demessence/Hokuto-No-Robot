package Hokuto.Part;

import Hokuto.Enemy.Enemy;
import Hokuto.Hokuto_no_Robot;
import Hokuto.Part.Scan.LockScan;
import Hokuto.Part.Scan.Scan;
import Hokuto.Part.Scan.SearchScan;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Radar extends Part {

    private ArrayList<Scan> scanList;
    private Scan currentScan;

    public Radar(Hokuto_no_Robot robot) {
	super(robot);
	
	this.scanList = new ArrayList<>(0);
	this.currentScan = null;
	this.scanList.add(new SearchScan(this));
	this.scanList.add(new LockScan(this));
    }
    public String getCurrentScanName() {
	return this.currentScan.getClass().getSimpleName();
    }

    public void setIndependantFromRobot(boolean b) {
	robot.setAdjustRadarForRobotTurn(b);
    }
    public boolean isIndependantFromRobot() {
	return robot.isAdjustRadarForRobotTurn();
    }
    public void setIndependantFromGun(boolean b) {
	robot.setAdjustRadarForGunTurn(b);
    }
    public boolean isIndependantFromGun() {
	return robot.isAdjustRadarForGunTurn();
    }

    public void scan() {
	if(!this.robot.hasTarget() || this.robot.countUpToDateEnemies()!=this.robot.getOthers())
	    this.currentScan = this.scanList.get(0);
	else
	    this.currentScan = this.scanList.get(1);
	
	this.robot.setTurnRadarRightRadians(this.currentScan.getHeading());
    }
    
    public void paint(Graphics2D g) {
	for(Enemy e : this.robot.getEnemies()) {
	    if(this.robot.hasTarget() && e.equals(this.robot.getTarget()))
		g.setColor(new Color(255, 0, 0, 255));
	    else
		g.setColor(new Color(255, 0, 255, 255));
	    g.drawRect((int)e.getX()-18, (int)e.getY()-18, 36, 36);
	    g.drawRect((int)e.getX()-19, (int)e.getY()-19, 38, 38);
	}
    }
}