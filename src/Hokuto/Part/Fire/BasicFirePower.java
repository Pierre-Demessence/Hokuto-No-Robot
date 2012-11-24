package Hokuto.Part.Fire;

import Hokuto.Enemy.Enemy;
import Hokuto.Part.Gun;
import Hokuto.Utils;
import robocode.Rules;

public class BasicFirePower extends FirePower {

    public BasicFirePower(Gun gun) {
	super(gun);
    }

    @Override
    public double getFirePower() {
	Enemy target = this.gun.getTarget();
	double maxPowerDistance = 150;
	double maxPower = 3;
	double power = maxPowerDistance/target.getDistance()*maxPower;
	power = Math.min(power, target.getEnergy()/4);
	power = Math.min(power, Rules.MAX_BULLET_POWER);
	power = Math.max(power, Rules.MIN_BULLET_POWER);
	return Utils.round(power, 1);
    }

}