package com.kteq.gwt.timesync.client.jsi;

import elemental2.core.JsNumber;
import jsinterop.annotations.JsFunction;

@JsFunction
public interface ChangeEventCallback {
    void onChange(JsNumber offset);
}
