package com.vmware.xenon.workshop;

import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.StatefulService;

public class SimpleEmployeeService extends StatefulService {

    public static class Employee extends ServiceDocument {
        public String name;
    }

    public SimpleEmployeeService() {
        super(Employee.class);
    }
}
