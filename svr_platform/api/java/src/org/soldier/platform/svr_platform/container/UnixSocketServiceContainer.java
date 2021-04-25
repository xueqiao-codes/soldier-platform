package org.soldier.platform.svr_platform.container;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.thrift.AFUNIXServerSocketTransport;


public class UnixSocketServiceContainer extends BaseServiceContainer {

    @SuppressWarnings("rawtypes")
    @Override
    protected AbstractServerArgs createArgs(Properties properties) throws Exception {
        File socketFile = new File("/data/run/service_" + serviceKey + ".sock");
        if (!socketFile.getParentFile().exists()) {
            if (!socketFile.getParentFile().mkdirs()) {
                throw new Exception("mkdirs failed for " + socketFile.getParentFile().getAbsolutePath());
            }
        }
        
        if (socketFile.exists()) {
            if (!socketFile.delete()) {
                throw new Exception("delete failed for " + socketFile.getAbsolutePath());
            }
        }
        AppLog.d("createArgs for " + serviceKey + ", socketFile=" + socketFile.getAbsolutePath());
        
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(new AFUNIXServerSocketTransport(socketFile));
        
        String workNumProps = properties.getProperty("workNum");
        if (StringUtils.isEmpty(workNumProps)) {
            int workingThreads = 2;
            int coreNum = Runtime.getRuntime().availableProcessors();
            if  (coreNum > 0) {
                workingThreads = coreNum*2;
            } 
            args.minWorkerThreads = workingThreads;
            args.maxWorkerThreads = workingThreads;
        } else {
            args.minWorkerThreads = Integer.parseInt(workNumProps);
            args.maxWorkerThreads = args.minWorkerThreads;
        }
        
        args.executorService(createExecutorService(args.minWorkerThreads, args.maxWorkerThreads));
        return args;
    }
    
    protected ExecutorService createExecutorService(int minWorkThread, int maxWorkThread) {
        BlockingQueue<Runnable> executorQueue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(minWorkThread
                , maxWorkThread
                , 60
                , TimeUnit.SECONDS
                , executorQueue);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected TServer createServer(AbstractServerArgs args) {
        return new TThreadPoolServer(TThreadPoolServer.Args.class.cast(args));
    }

    @Override
    protected void willStartService() {
    }

    @Override
    protected boolean willServe() {
        return true;
    }

}
