package Hokuto.Part.Targeting;

import Hokuto.Part.Gun;
import Hokuto.VirtualBullet;
import java.util.ArrayList;

public abstract class Targeting {

    protected Gun gun;
    protected ArrayList<VirtualBullet> virtualBullets;
    protected int hit;
    
    public Targeting(Gun gun) {
	this.gun = gun;
	this.virtualBullets = new ArrayList<>(0);
	this.hit = 0;
    }
    protected abstract double newX();
    protected abstract double newY();
    public double getX() {
	return Math.max(18, Math.min(this.gun.getBattleFieldWidth()-18, this.newX()));
    }
    public double getY() {
	return Math.max(18, Math.min(this.gun.getBattleFieldHeight()-18, this.newY()));
    }
    
    public double getTimeFromBulletToEnemy() {
	return (long)(this.gun.getTarget().getDistance() / this.gun.getBulletSpeed(this.gun.getFirePower()));
    }
    
    public double getAcuracy() {
	return (this.getInactiveVirtualBullets()>0 ? (double)this.hit/this.getInactiveVirtualBullets()*100 : 0);
    }
    public void hit() {
	this.hit++;
    }
    
    public ArrayList<VirtualBullet> getVirtualBullets() {
	return this.virtualBullets;
    }
    public int getInactiveVirtualBullets() {
	int res = 0;
	for(VirtualBullet b : this.virtualBullets) {
	    if(!b.isActive())
		res++;
	}
	return res;
    }
    public void addVirtualBullet(VirtualBullet b) {
	this.virtualBullets.add(b);
    }
}