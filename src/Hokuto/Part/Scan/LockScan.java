package Hokuto.Part.Scan;

import Hokuto.Part.Radar;
import robocode.util.Utils;

public class LockScan extends Scan {

    public LockScan(Radar radar) {
	super(radar);
    }

    @Override
    public double getHeading() {
	return 2.0*Utils.normalRelativeAngle(this.radar.getHeading() + this.radar.getTarget().getBearing() - this.radar.getRadarHeading());
    }

}