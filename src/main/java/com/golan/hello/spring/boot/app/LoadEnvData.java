package com.golan.hello.spring.boot.app;

import com.golan.hello.spring.iam.organizations.Organization;

import java.util.ArrayList;
import java.util.List;

class LoadEnvData {


    static List<Organization> getAllOrganizations() {
        final ArrayList<Organization> res = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            final String orgName = String.format("org_%03d", i);
            res.add(new Organization(orgName, orgName));
        }
        return res;
    }


}
