package com.vmware.xenon.workshop;

import com.vmware.xenon.common.FactoryService;
import com.vmware.xenon.common.Service;

public class SimpleEmployeeFactoryService extends FactoryService {

    public static final String SELF_LINK = "/sample/simple-employees";

    public SimpleEmployeeFactoryService() {
        super(SimpleEmployeeService.Employee.class);
    }

    @Override
    public Service createServiceInstance() throws Throwable {
        return new SimpleEmployeeService();
    }
}
