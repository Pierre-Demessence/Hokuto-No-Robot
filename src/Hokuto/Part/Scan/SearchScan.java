package Hokuto.Part.Scan;

import Hokuto.Part.Radar;

public class SearchScan extends Scan {

    public SearchScan(Radar radar) {
	super(radar);
    }

    @Override
    public double getHeading() {
	return Double.POSITIVE_INFINITY;
    }

}