package org.soldier.platform.route_dao.test;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.route.dao.client.RouteDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestMain {

    public static void main(String[] args) {
        System.out.println("TestMain started!");
        
        AppLog.init("route_dao_test");
        
        AppLog.t("this is a trace");
        AppLog.d("this is a debug");
        
        try {
            RouteDaoStub stub = new RouteDaoStub();
            stub.setPeerAddr("cmd20.soldier-service-alias.svc");
            System.out.println("route version=" + stub.getLastRouteVersion());
        } catch (ErrorInfo e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}
