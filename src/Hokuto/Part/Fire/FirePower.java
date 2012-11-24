package Hokuto.Part.Fire;

import Hokuto.Part.Gun;

public abstract class FirePower {

    protected Gun gun;
    
    public FirePower(Gun gun) {
	this.gun = gun;
    }

    public abstract double getFirePower();
}