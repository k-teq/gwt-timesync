package com.kteq.gwt.timesync.client.jsi;

import com.kteq.gwt.timesync.client.Timesync;
import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Any;
import jsinterop.base.Js;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public final class TimesyncOptions {
    private int delay;
    private int interval;
    private int repeat;
    private int timeout;

    private String[] peers;
    private Any server; //string or array of strings

    @JsOverlay
    public final TimesyncOptions setDelay(int delay) {
        this.delay = delay;
        return this;
    }
    @JsOverlay
    public final TimesyncOptions setInterval(int interval) {
        this.interval = interval;
        return this;
    }
    @JsOverlay
    public final TimesyncOptions setRepeat(int repeat) {
        this.repeat = repeat;
        return this;
    }

    @JsOverlay
    public final TimesyncOptions setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @JsOverlay
    public final TimesyncOptions setServer(String server) {
        this.server = Js.uncheckedCast(server);
        return this;
    }
    @JsOverlay
    public final TimesyncOptions setServer(String[] servers) {
        this.server = Js.uncheckedCast( JsArray.from(servers));
        return this;
    }
    @JsOverlay
    public final TimesyncOptions setPeers(String[] peers) {
        this.peers =  peers;
        return this;
    }

}
