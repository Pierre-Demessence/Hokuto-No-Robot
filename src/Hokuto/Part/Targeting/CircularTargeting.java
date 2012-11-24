package Hokuto.Part.Targeting;

import Hokuto.Enemy.Enemy;
import Hokuto.Part.Gun;

public class CircularTargeting extends Targeting {

    public CircularTargeting(Gun gun) {
	super(gun);
    }
    
    @Override
    public double newX() {
	Enemy target = this.gun.getTarget();
	double turnRate = target.getTurnRate();
	double initialTargetHeading = target.getHeading();
	double finalTargetHeading   = initialTargetHeading + turnRate * this.getTimeFromBulletToEnemy();
	return target.getX() - target.getVelocity() / turnRate *(Math.cos(finalTargetHeading) - Math.cos(initialTargetHeading));
    }
    
    @Override
    public double newY() {
	Enemy target = this.gun.getTarget();
	double turnRate = target.getTurnRate();
	double initialTargetHeading = target.getHeading();
	double finalTargetHeading   = initialTargetHeading + turnRate * this.getTimeFromBulletToEnemy();
	return target.getY() - target.getVelocity() / turnRate *(Math.sin(initialTargetHeading) - Math.sin(finalTargetHeading));
    }

}