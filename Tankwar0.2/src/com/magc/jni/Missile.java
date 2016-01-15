package com.magc.jni;

import Tank.java;

public class Missile {
	static {
		System.loadLibrary(Missile);
	}
	
	public native Missile(int x, int y, Tank.Direction dir, boolean good);
}
