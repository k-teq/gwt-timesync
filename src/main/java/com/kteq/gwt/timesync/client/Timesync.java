package com.kteq.gwt.timesync.client;

import com.google.gwt.core.client.Scheduler;
import com.kteq.gwt.timesync.client.jsi.ChangeEventCallback;
import com.kteq.gwt.timesync.client.jsi.ErrorEventCallback;
import com.kteq.gwt.timesync.client.jsi.SyncEventCallback;
import com.kteq.gwt.timesync.client.jsi.TimesyncOptions;
import elemental2.core.JsDate;
import elemental2.core.JsNumber;
import jsinterop.annotations.*;

@JsType(isNative = true)
public final class Timesync {


    //use TimesyncUtils.create(...)
    protected Timesync() {
    }


    public native JsNumber now();
    public native void sync();


    @JsProperty
    public native JsNumber getOffset();
    @JsProperty
    public native TimesyncOptions getOptions();



    //EVENTS
    private native void on(String eventName, Object callback);

    @JsOverlay
    public void onChange(ChangeEventCallback callback) {
        on("change", callback);
    }
    @JsOverlay
    public void onError(ErrorEventCallback callback) {
        on("error", callback);
    }
    @JsOverlay
    public void onSync(SyncEventCallback callback) {
        on("sync", callback);
    }




    @JsFunction
    public interface ScheduledCommand {
        public void execute(Timesync ts);
    }
    //un bello scheduler in ts time
    @JsOverlay
    public int scheduleAt(JsDate scheduledDateTime, ScheduledCommand command) {
        int waitingTimeMs = (int) (scheduledDateTime.valueOf() - Timesync.this.now().valueOf());

        Scheduler.get().scheduleFixedDelay( () -> {
            command.execute(Timesync.this );
            return false;
        },  waitingTimeMs);

        return waitingTimeMs;
    }

    @JsOverlay
    public static int i() {
        return 42;
    }

}
