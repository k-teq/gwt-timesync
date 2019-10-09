package com.kteq.gwt.timesync.server;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import org.eclipse.jetty.server.AsyncContextState;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SSESource extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        System.out.println(req.getSession(true).getId());

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.flushBuffer();
        AsyncContext async = req.startAsync();

        System.out.println(async);

//        new Thread() {
//            public void run() {
//                try {
//                    for (int i = 0; i < 3; i++) {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        ServletOutputStream outputStream = async.getResponse().getOutputStream();
//                        if (outputStream.isReady()) {
//                            String message = "data: "+ Instant.now().getEpochSecond() +" \n\n";
//                            outputStream.write(message.getBytes(StandardCharsets.UTF_8), 0, message.length());
//                            outputStream.flush();
//                            async.getResponse().flushBuffer();
//                        } else {
//                            System.out.println("not ready!");
//                        }
//                        System.out.println("wrote!");
//                    }
//                } catch (IOException e) {
//                    async.complete();
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        Timer timer = new Timer(true);


        long tillNextRoundMinute = (60 - Instant.now().getEpochSecond() % 60) * 1000;

        System.err.println("First run in: " + tillNextRoundMinute + " ms");
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            ServletOutputStream outputStream = async.getResponse().getOutputStream();
                            if (outputStream.isReady()) {
                                String message = "data: " + Instant.now().getEpochSecond() + " \n\n";
                                outputStream.write(message.getBytes(StandardCharsets.UTF_8), 0, message.length());
                                outputStream.flush();
                                async.getResponse().flushBuffer();
                            } else {
                                System.out.println("not ready!");
                            }
                            System.out.println("wrote!");
                        } catch (IOException e) {
                            async.complete();
                            e.printStackTrace();
                        }
                    }
                }, tillNextRoundMinute, 10 * 1000
        );


    }

}

