package com.vmware.xenon.workshop;

import com.vmware.xenon.common.*;
import com.vmware.xenon.services.common.RootNamespaceService;

import java.util.function.Consumer;

/**
 * Demonstrate subscription.
 */
public class SubscriptionDemo {

    public static class MySubscriptionService extends StatefulService {

        public static String FACTORY_LINK = "/me";

        public static FactoryService createFactory() {
            return FactoryService.create(MySubscriptionService.class);
        }

        public MySubscriptionService() {
            super(ServiceDocument.class);
        }

        @Override
        public void handleGet(Operation get) {
            Operation op = Operation.createPost(getUri()).setBody("ABC");
            ((UtilityService)getUtilityService("")).notifySubscribers(op);
            super.handleGet(get);
        }
    }

    public static class MySubscriptionHost extends ServiceHost {
        @Override
        public ServiceHost start() throws Throwable {
            super.start();

            startDefaultCoreServicesSynchronously();

            // Start the root namespace factory: this will respond to the root URI (/) and list all
            // the factory services.
            super.startService(new RootNamespaceService());

            this.startFactory(MySubscriptionService.class, MySubscriptionService::createFactory);

            // subscription callback
            Consumer<Operation> target = (notifyOp) -> {
                System.out.println("notification: " + notifyOp);
                notifyOp.complete();
            };

            Operation createSub = Operation.createPost(
                    UriUtils.buildUri(this, MySubscriptionService.FACTORY_LINK))
                    .setReferer(this.getUri());


            this.registerForServiceAvailability((o, e) -> {
                this.startSubscriptionService(createSub, target);
//                this.startReliableSubscriptionService(createSub, target);

            }, MySubscriptionService.FACTORY_LINK);
            return this;
        }
    }

    public static void main(String[] args) throws Throwable {
        MySubscriptionHost host = new MySubscriptionHost();
        host.initialize(args);
        host.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            host.stop();
        }));
    }
}
