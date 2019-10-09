package com.kteq.gwt.timesync.client.jsi;

import jsinterop.annotations.JsFunction;

@JsFunction
public interface SyncEventCallback {
    void onSync(String x);
}
