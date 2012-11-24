package Hokuto.Part.Scan;

import Hokuto.Part.Radar;

public abstract class Scan {

    protected Radar radar;

    public Scan(Radar radar) {
	this.radar = radar;
    }

    public abstract double getHeading();
}