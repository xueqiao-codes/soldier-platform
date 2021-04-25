namespace java org.soldier.platform.demo.hello

include "comm.thrift"

service(16) HelloworldService {
    string helloworld(comm.PlatformArgs platformArgs) throws (comm.ErrorInfo err);
}
