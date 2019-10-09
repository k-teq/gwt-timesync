package com.kteq.gwt.timesync.client;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name="console")
public abstract class Console {

	private Console() {};
	public static native void log(Object ms);
	public static native void log(Object a, Object b);
	public static native void log(Object a, Object b, Object c);
	public static native void log(Object a, Object b, Object c, Object d);

}
