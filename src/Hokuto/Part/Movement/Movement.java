package Hokuto.Part.Movement;

import Hokuto.Part.Body;
import java.awt.Graphics2D;

public abstract class Movement {

    protected Body body;
    protected int dodge;
    
    public Movement(Body body) {
	this.body = body;
    }

    public abstract void move();
    public abstract void paint(Graphics2D g);
    
}