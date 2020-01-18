package com.carrier.smpp.demo.client;

import java.util.UUID;

public final class CodeGenerator {
	private CodeGenerator() {

	}


	public synchronized static String generateCode(){
		UUID code = UUID.randomUUID();
		String uuid= convertObjectToString(code);
		uuid=uuid.replace("-", "");
		return uuid;
	}
	private static String convertObjectToString(Object object){
		return String.valueOf(object);
	}

}
