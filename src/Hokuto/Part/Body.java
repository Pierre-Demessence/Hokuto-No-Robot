package Hokuto.Part;

import Hokuto.Hokuto_no_Robot;
import Hokuto.Part.Movement.Movement;
import Hokuto.Part.Movement.StopAndGoMovement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Body extends Part {

    private ArrayList<Movement> movementList;
    private Movement currentMovement;

    public Body(Hokuto_no_Robot robot) {
	super(robot);
	
	this.movementList = new ArrayList<>(0);
	this.currentMovement = null;
	this.movementList.add(new StopAndGoMovement(this));
    }

    public String getCurrentMovementName() {
	return this.currentMovement.getClass().getSimpleName();
    }
    
    public void setMaxVelocity(double newMaxVelocity) {
	this.robot.setMaxVelocity(newMaxVelocity);
    }
    public void setMaxTurnRate(double newMaxTurnRate) {
	this.robot.setMaxTurnRate(newMaxTurnRate);
    }
    public void setAhead(double distance) {
	this.robot.setAhead(distance);
    }
    public void setTurnRightRadians(double radians) {
	this.robot.setTurnRightRadians(radians);
    }

    public void move() {
	this.currentMovement = this.movementList.get(0);
	this.currentMovement.move();
    }
    
    public void simulate() {
	
    }
    
    public void paint(Graphics2D g) {
	for(Movement m : this.movementList) {
	    if(m.equals(this.currentMovement))
		g.setColor(Color.YELLOW);
	    else
		g.setColor(Color.ORANGE);
	    m.paint(g);
	}
    }
}