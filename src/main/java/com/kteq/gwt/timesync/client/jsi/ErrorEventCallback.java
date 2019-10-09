package com.kteq.gwt.timesync.client.jsi;

import jsinterop.annotations.JsFunction;
import jsinterop.base.Any;

@JsFunction
public interface ErrorEventCallback {
    void onError(Any error);
}
