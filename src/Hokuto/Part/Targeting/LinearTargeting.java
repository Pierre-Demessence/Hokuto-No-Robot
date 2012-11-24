package Hokuto.Part.Targeting;

import Hokuto.Part.Gun;

public class LinearTargeting extends Targeting {

    public LinearTargeting(Gun gun) {
	super(gun);
    }
    
    @Override
    public double newX() {
	return this.gun.getTarget().getX() + Math.sin(this.gun.getTarget().getHeading()) * this.gun.getTarget().getVelocity() * this.getTimeFromBulletToEnemy();
    }
    
    @Override
    public double newY() {
	return this.gun.getTarget().getY() + Math.cos(this.gun.getTarget().getHeading()) * this.gun.getTarget().getVelocity() * this.getTimeFromBulletToEnemy();
    }
    
}