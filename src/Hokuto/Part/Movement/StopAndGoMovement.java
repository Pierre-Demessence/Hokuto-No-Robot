package Hokuto.Part.Movement;

import Hokuto.Part.Body;
import Hokuto.Enemy.Enemy;
import Hokuto.VirtualBullet;
import java.awt.Graphics2D;
import java.util.ArrayList;
import robocode.Rules;
import robocode.util.Utils;

public class StopAndGoMovement extends Movement {
    private int direction = 1;
    
    public StopAndGoMovement(Body body) {
	super(body);
    }
    

    @Override
    public void move() {
	this.body.setMaxVelocity(Rules.MAX_VELOCITY);
	this.body.setMaxTurnRate(Rules.MAX_TURN_RATE);
	if(this.body.hasTarget()) {
	    Enemy target = this.body.getTarget();
	    this.body.setTurnRightRadians(Utils.normalRelativeAngle(target.getBearing()+Math.PI/2));
	    if(this.body.getDistanceRemaining()==0.0 && target.hasFired()) {
		ArrayList<VirtualBullet> bullets = target.getBullets();
		double power = bullets.get(bullets.size()-1).getPower();
		double time = (1+power/5)/this.body.getGunCoolingRate();
		double distance = (40+(time-10)*8)-5;
		this.body.setAhead(distance*this.direction);
		if(Hokuto.Utils.random(1, 5)>2)
		    this.direction *= -1;
	    }
	}
	    
    }

    @Override
    public void paint(Graphics2D g) {
    }

}