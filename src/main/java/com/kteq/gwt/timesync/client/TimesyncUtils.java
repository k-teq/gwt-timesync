package com.kteq.gwt.timesync.client;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Window;
import com.kteq.gwt.timesync.client.jsi.*;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;



public class TimesyncUtils {


    public interface Resources extends ClientBundle {
        Resources INSTANCE = GWT.create(Resources.class);
        @Source("node_modules/timesync/dist/timesync.js")
        TextResource timesyncjs();
    }

    private static boolean injected = false;
    public static void ensureInjected() {
        if (!injected) {
            injected = true;
            ScriptInjector.fromString(Resources.INSTANCE.timesyncjs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
            //StyleInjector.inject(Resources.INSTANCE.dygraphcss().getText());
        }
    }

    @JsMethod(namespace = "timesync", name = "create")
    private static native Timesync _create(TimesyncOptions options);

    public static Timesync create(TimesyncOptions options) {
        ensureInjected();
        return _create(options);
    }


    @JsFunction
    public interface SyncCallback {
        public void onSync(Timesync ts);
    }
    public static void createAndWaitTillSync(TimesyncOptions options, SyncCallback syncCallback) {
        ensureInjected();
        Timesync ts = _create(options);
        ts.onSync( (s) -> {
            if("end".equals(s)) {
                syncCallback.onSync(ts);
            }
        });
        ts.onError( (e) -> {
            Console.log("error", e);
            syncCallback.onSync(null); //convenzione
        });

    }


}
