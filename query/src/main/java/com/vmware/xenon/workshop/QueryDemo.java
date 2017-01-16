package com.vmware.xenon.workshop;

import com.vmware.xenon.common.ServiceHost;
import com.vmware.xenon.services.common.ExampleService;

/**
 * StatelessService demo
 */
public class QueryDemo {

    public static class QuerySampleServiceHost extends ServiceHost {
        @Override
        public ServiceHost start() throws Throwable {
            super.start();
            startDefaultCoreServicesSynchronously();
            this.startFactory(ExampleService.class, ExampleService::createFactory);
            return this;
        }
    }

    public static void main(String[] args) throws Throwable {
        ServiceHost host = new QuerySampleServiceHost();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            host.stop();
        }));
        host.initialize(args);
        host.start();
    }

}
