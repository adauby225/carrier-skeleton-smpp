package com.carrier.smpp;

import java.util.ArrayList;
import java.util.List;

public class Carrier {
	private List<CarrierSmppInstance>carrierEntities = new ArrayList<>();
	public Carrier(List<CarrierSmppInstance> carrierEntities) {
		this.carrierEntities = carrierEntities;
	}
	public void startEntities() {
		for(CarrierSmppInstance smppEntity: carrierEntities)
			smppEntity.start();
	}

}
