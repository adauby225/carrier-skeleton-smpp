package com.carrier.smpp;

import java.util.ArrayList;
import java.util.List;

import com.carrier.smpp.server.CarrierSmppEntity;

public class Carrier {
	private List<CarrierSmppEntity>carrierEntities = new ArrayList<>();
	public Carrier(List<CarrierSmppEntity> carrierEntities) {
		this.carrierEntities = carrierEntities;
	}
	public void startEntities() {
		for(CarrierSmppEntity smppEntity: carrierEntities)
			smppEntity.start();
	}

}
