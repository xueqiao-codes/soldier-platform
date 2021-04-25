namespace java org.soldier.platform.proxy.test.ao

include "../comm.thrift"

service(3) proxy_test_ao {
      string 1:testEcho(1:comm.PlatformArgs platformArgs, 2:string content) throws (1:comm.ErrorInfo err);
}
