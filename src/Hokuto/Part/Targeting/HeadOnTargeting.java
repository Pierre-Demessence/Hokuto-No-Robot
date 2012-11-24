package Hokuto.Part.Targeting;

import Hokuto.Part.Gun;

public class HeadOnTargeting extends Targeting {

    public HeadOnTargeting(Gun gun) {
	super(gun);
    }
    
    @Override
    public double newX() {
	return this.gun.getTarget().getX();
    }
    
    @Override
    public double newY() {
	return this.gun.getTarget().getY();
    }

}