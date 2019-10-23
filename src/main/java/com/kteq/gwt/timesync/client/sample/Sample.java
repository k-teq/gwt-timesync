package com.kteq.gwt.timesync.client.sample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.kteq.gwt.timesync.client.Console;
import com.kteq.gwt.timesync.client.Timesync;
import com.kteq.gwt.timesync.client.TimesyncUtils;
import com.kteq.gwt.timesync.client.jsi.TimesyncOptions;
import elemental2.core.JsDate;


public class Sample implements EntryPoint {


    public Sample() {
    }

    @Override
    public void onModuleLoad() {

//
//
//        Timesync x = TimesyncUtils.create(
//                new TimesyncOptions()
//                        .setServer("timesync")
//                        .setInterval(1000000)
//        );
//
//
//        x.onChange((o) -> Console.log("CHANGE EVENT: ", o));
//        x.onChange((o) -> Console.log("SECOND CHANGE EVENT: ", o));
//        x.onSync((o) -> {
//            Console.log("Sync EVENT: ", o);
//            if ("end".equals(o)) {
//                Console.log("In Sync");
//            }
//        });
//        x.onError((o) -> Console.log("Error EVENT: ", o));
//
//
//        Scheduler.get().scheduleFixedDelay(
//
//                () -> {
//                    Console.log("now SERVER: ", JsDate.create(x.now().valueOf()), "OFFSET: ", x.getOffset());
//                    Console.log("now CLIENT", JsDate.create(JsDate.now()));
//                    return true;
//                }, 1000
//        );
//
//
//
        //the managed way
        RootPanel.get().add(new HTML("starting Syncronization: " + new JsDate( JsDate.now())));


        Window.alert("cxxxx" + Timesync.i());

        TimesyncUtils.createAndWaitTillSync(
                new TimesyncOptions().setServer("timesync").setInterval(1000000),
                (ts) -> {

                    RootPanel.get().add(new HTML("we're in sync at :" + new JsDate( JsDate.now())));
                    RootPanel.get().add(new HTML("server time is :" + new JsDate( ts.now().valueOf() )));

                    Scheduler.get().scheduleFixedDelay(
                            () -> {
                                String a = "now SERVER: " + new JsDate(ts.now().valueOf()) + " OFFSET: " + ts.getOffset();
                                String b = "now CLIENT " + new JsDate(JsDate.now());
                                RootPanel.get().add(new HTML(a));
                                RootPanel.get().add(new HTML(b));

                                return true;
                            }, 10000
                    );


                    //questo funziona (fa qualcosa) solo perche' il server e'
                    //fatto in modo da ritornare esattamente 10 minuti avanti
                    int ed = ts.scheduleAt( new elemental2.core.JsDate( JsDate.now() + 11*60*1000 ),
                            (tss) -> Window.alert("Eccomi"));

                    Console.log("ed: " + ed);
                }
        );


    }


}
