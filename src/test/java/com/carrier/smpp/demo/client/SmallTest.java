package com.carrier.smpp.demo.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmallTest {
	private static final Logger logger = LogManager.getLogger(SmallTest.class);
	public static void main(String[] args) {
		
		Map<Integer, MyClass>map = new HashMap<>();
		map.put(1, new MyClass(CodeGenerator.generateCode(), "test"));
		
		MyClass instance1 = map.get(1);
		MyClass instance2 = map.get(1);
		logger.info("instance 1: " + instance1.toString());
		logger.info("instance 2: " + instance2.toString());
		
		logger.info("instance are equals : "+ instance1.equals(instance2));
	}
}

class MyClass{
	private String code;
	private String name;

	public MyClass(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyClass other = (MyClass) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MyClass [code=" + code + ", name=" + name + "]";
	}
	
	
	
	
}


