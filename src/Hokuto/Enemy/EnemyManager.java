package Hokuto.Enemy;

import Hokuto.Enemy.Enemy;
import Hokuto.Hokuto_no_Robot;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class EnemyManager {
    public enum choice {NEWEST, OLDEST, CLOSEST, FAREST, WEAKEST, STRONGEST}
    
    private Hokuto_no_Robot robot;
    private ArrayList<Enemy> enemies;
    private Enemy target;

    public EnemyManager(Hokuto_no_Robot robot) {
	this.robot = robot;
	this.enemies = new ArrayList<>(0);
	this.target = null;
    }

    public void chooseTarget(choice c) {
	switch(c) {
	    default:
	    case NEWEST:
		this.target = getNewestEnemy();
		break;
	    case OLDEST:
		this.target = getOldestEnemy();
		break;
	    case CLOSEST:
		this.target = getClosestEnemy();
		break;
	    case FAREST:
		this.target = getFarestEnemy();
		break;
	    case WEAKEST:
		this.target = getWeakestEnemy();
		break;
	    case STRONGEST:
		this.target = getStrongestEnemy();
		break;
	}
    }
    public boolean hasTarget() {
	return this.target != null;
    }
    public boolean isTargetOld(int time) {
	return this.robot.getTime() - this.target.getTime() > time;
    }
    public Enemy getTarget() {
	return target;
    }
    public Enemy[] getEnemies() {
	return enemies.toArray(new Enemy[0]);
    }
    public int count() {
	return this.enemies.size();
    }
    public int countUptoDateEnemies() {
	int res = 0;
	for(Enemy e : this.enemies) {
	    if(this.robot.getTime() - e.getTime() <= 1)
		res++;
	}
	return res;
    }

    public void update(ScannedRobotEvent e, AdvancedRobot r) {
	Enemy enemy = this.getEnemyByName(e.getName());
	if (enemy == null) {
	    this.add(new Enemy(e, r));
	} else {
	    enemy.update(e, r);
	}
    }
    public void enemyDeath(String name) {
	this.enemies.remove(this.getEnemyByName(name));
	this.target=null;
    }

    private void add(Enemy e) {
	this.enemies.add(e);
    }

    private Enemy get(int i) {
	return this.enemies.get(i);
    }
    private Enemy getEnemyByName(String name) {
	for (Enemy e : this.enemies) {
	    if (e.getName().equals(name)) {
		return e;
	    }
	}
	return null;
    }
    private Enemy getNewestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getTime() < this.get(i).getTime()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    private Enemy getOldestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getTime() > this.get(i).getTime()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    private Enemy getClosestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getDistance() > this.get(i).getDistance()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    private Enemy getFarestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getDistance() < this.get(i).getDistance()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    private Enemy getWeakestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getEnergy() > this.get(i).getEnergy()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    private Enemy getStrongestEnemy() {
	Enemy e = enemies.get(0);
	for (int i = 1; i < this.count(); i++) {
	    if (e.getEnergy() < this.get(i).getEnergy()) {
		e = this.get(i);
	    }
	}
	return e;
    }
    
    public void paint(Graphics2D g) {
	for(Enemy e : this.enemies) {
	    if(e.equals(this.target))
		g.setColor(Color.RED);
	    else
		g.setColor(Color.ORANGE);
	    e.paint(g, this.robot.getTime());
	}
    }
}