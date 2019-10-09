package com.kteq.gwt.timesync.server;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class TimeServer extends HttpServlet {



    // Declare any bean-like interface with matching getters and setters, no base type is necessary
    interface TimeRequest {
        //{"jsonrpc":"2.0","id":0,"method":"timesync"}
        String getJsonrpc();
        long getId();
        String getMethod();

        void setJsonrpc(String v);
        void setId(long v);
        void setMethod(String v);
    }

    interface TimeResponse {
        //{"jsonrpc": "2.0", "id": "12345", "result": 1423151204595}
        String getJsonrpc();
        long getId();
        long getResult();

        void setJsonrpc(String v);
        void setId(long v);
        void setResult(long v);
    }

    // Declare the factory type
    interface TimeRFactory extends AutoBeanFactory {
        AutoBean<TimeRequest> request();
        AutoBean<TimeResponse> response();
    }


    String serializeToJson(TimeResponse person) {
        // Retrieve the AutoBean controller
        AutoBean<TimeResponse> bean = AutoBeanUtils.getAutoBean(person);

        return AutoBeanCodex.encode(bean).getPayload();
    }

    TimeRequest deserializeFromJson(String json, TimeRFactory factory) {
        AutoBean<TimeRequest> bean = AutoBeanCodex.decode(factory, TimeRequest.class, json);
        return bean.as();
    }


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //super.doGet(req, resp);
//        System.err.println("GET QS: " + req.getQueryString());
//
//        String body = req.getReader().lines()
//                .reduce("", (accumulator, actual) -> accumulator + actual);
//
//        System.err.println("GET Body: " + body);
//
//
//        resp.getWriter().println("hello");
//
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.err.println("POST QS" + req.getQueryString());
        String body = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        System.err.println("POST Body: " + body);


        TimeRFactory factory = AutoBeanFactorySource.create(TimeRFactory.class);

        TimeRequest tr = deserializeFromJson(body, factory);

        System.err.println("meth: " + tr.getMethod());
        System.err.println("id: " + tr.getId());
        System.err.println("ver: " + tr.getJsonrpc());

        //creating response
        TimeResponse tres = factory.response().as();

        tres.setId(tr.getId());
        tres.setJsonrpc(tr.getJsonrpc());

        tres.setResult( new Date().getTime() + 10*60*1000); //10 minuti avanti !!

        String responseJson =
                String.format("{\"result\":%d,\"jsonrpc\":\"%s\",\"id\":\"%d\"}",
                        tres.getResult(), tres.getJsonrpc(), tres.getId());

                //serializeToJson(tres);


        System.err.println("RESPONSE: " + responseJson);

        resp.setContentType("application/json");
        resp.getWriter().println(responseJson);
    }


}
