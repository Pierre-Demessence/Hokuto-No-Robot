package Hokuto;

import Hokuto.Part.Body;
import Hokuto.Part.Radar;
import Hokuto.Part.Gun;
import Hokuto.Enemy.Enemy;
import Hokuto.Enemy.EnemyManager;
import Hokuto.Enemy.EnemyManager.choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Hokuto_no_Robot extends AdvancedRobot {
    private EnemyManager enemyManager;
    private Body body;
    private Radar radar;
    private Gun gun;
    
    private boolean[] paint;
    private final int PAINT_BODY = 0;
    private final int PAINT_RADAR = 1;
    private final int PAINT_GUN = 2;
    private final int PAINT_ENEMY = 3;
    private final int PAINT_TEXT = 4;
    
    @Override
    public void run() {
	this.enemyManager = new EnemyManager(this);
	this.body = new Body(this);
	this.radar = new Radar(this);
	this.gun = new Gun(this);
	
	this.radar.setIndependantFromGun(true);
	this.radar.setIndependantFromRobot(true);
	this.gun.setIndependantFromRobot(true);
	
	this.paint = new boolean[5];
	
	this.paint[PAINT_BODY] = true;
	this.paint[PAINT_RADAR] = true;
	this.paint[PAINT_GUN] = true;
	this.paint[PAINT_ENEMY] = true;
	this.paint[PAINT_TEXT] = true;
	
	while(true) {
	    radar.scan();
	    body.move();
	    gun.aim();
	    gun.fire();
	    this.execute();
	    //body.simulate();
	    gun.simulate();
	}
    }
    
    public boolean hasTarget() {
	return this.enemyManager.hasTarget();
    }
    public boolean isTargetOld(int time) {
	return this.enemyManager.isTargetOld(time);
    }
    public Enemy getTarget() {
	return this.enemyManager.getTarget();
    }
    public Enemy[] getEnemies() {
	return this.enemyManager.getEnemies();
    }
    public int countEnemies() {
	return this.enemyManager.count();
    }
    public int countUpToDateEnemies() {
	return this.enemyManager.countUptoDateEnemies();
    }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
         this.enemyManager.update(e, this);
	 this.enemyManager.chooseTarget(choice.CLOSEST);
    }
    @Override
    public void onRobotDeath(RobotDeathEvent e) {
	this.enemyManager.enemyDeath(e.getName());
    }
    
    @Override
    public void onKeyTyped(KeyEvent e ) {
	if(e.getKeyChar()=='b')
	    this.paint[PAINT_BODY] = !this.paint[PAINT_BODY];
	if(e.getKeyChar()=='r')
	    this.paint[PAINT_RADAR] = !this.paint[PAINT_RADAR];
	if(e.getKeyChar()=='g')
	    this.paint[PAINT_GUN] = !this.paint[PAINT_GUN];
	if(e.getKeyChar()=='e')
	    this.paint[PAINT_ENEMY] = !this.paint[PAINT_ENEMY];
	if(e.getKeyChar()=='t')
	    this.paint[PAINT_TEXT] = !this.paint[PAINT_TEXT];
    }
    
    @Override
    public void onPaint(Graphics2D g) {
	
	if(this.paint[PAINT_BODY])
	    body.paint(g);
	if(this.paint[PAINT_RADAR])
	    radar.paint(g);
	if(this.paint[PAINT_GUN])
	    gun.paint(g);
	if(this.paint[PAINT_ENEMY])
	    enemyManager.paint(g);
	if(this.paint[PAINT_TEXT]) {
	    g.setColor(new Color(0, 255, 0, 255));
	    g.drawString("Current FirePower : "+this.gun.getCurrentFirepowerName()+(this.hasTarget() ? " ("+this.gun.getFirePower()+")" : ""), 5, 39);
	    g.drawString("Current Targeting : "+this.gun.getCurrentTargetingName()+" ("+Hokuto.Utils.round(this.gun.getMoreAccurate().getAcuracy(), 1)+"%)", 5, 27);
	    g.drawString("Current Movement : "+this.body.getCurrentMovementName(), 5, 3);
	    g.drawString("Current Scan : "+this.radar.getCurrentScanName()+" ("+this.enemyManager.countUptoDateEnemies()+")", 5, 15);
	}
	g.setColor(Color.BLACK);
	g.setFont(g.getFont().deriveFont(Font.BOLD));
	g.drawString("Debug :", 600, 100);
	for(int i=0 ; i < this.paint.length ; i++) {
	    g.setColor((this.paint[i] ? Color.GREEN : Color.RED));
	    String text = "";
	    switch(i) {
	    case 0:
		text = "B - Body";
		break;
	    case 1:
		text = "R - Radar";
		break;
	    case 2:
		text = "G - Gun";
		break;
	    case 3:
		text = "E - Enemies";
		break;
	    case 4:
		text = "T - Text";
		break;
	    }
	    g.drawString(text, 600, 100-15*(i+1));
	}
	
    }
}