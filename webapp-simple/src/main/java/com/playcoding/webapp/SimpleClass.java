package com.playcoding.webapp;

import com.google.gson.Gson;

/**
 * this is a simple class within webapp
 * 
 * @author sswater
 */
public class SimpleClass {

	protected int i = 0;
	
	public int getCount() {
		return ++i;
	}
	
	public String toString() {
		return new Gson().toJson(this);
	}

}
