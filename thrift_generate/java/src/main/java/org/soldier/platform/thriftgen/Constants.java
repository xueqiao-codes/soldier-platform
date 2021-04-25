package org.soldier.platform.thriftgen;

public class Constants {

    public static final boolean DEBUG = false;
    private static final String PATH_SEPERATOR = DEBUG ? "\\\\" : "/";

    // thrift 文件所在目录
    public static final String PATH_BASE = DEBUG ? "D:\\\\coding-git\\\\ThriftGen\\\\ThriftGenerate2\\\\src\\\\test\\\\"
            : "/public/scripts/IDL/";

    // thrift 编译脚本路径
    private static final String THRIFT_CMD = DEBUG ? (PATH_BASE + "tools" +  PATH_SEPERATOR + "thrift-0.10.0.exe")
            : "/public/scripts/bin/thrift";
    public static final String JAVA_WITH_PYTEST = "java-with-py-test";

    public static final String ROOT_FOLDER_PATH = PATH_BASE + (DEBUG ? "ThriftFiles\\\\" : "");
    public static final String GENERATE_CMD = THRIFT_CMD + " -r -o %1$s -gen %2$s %3$s";
    public static final String JAVA_WITH_PYTEST_CMD = "/public/scripts/bin/java_svr_with_py_test.sh %1$s %2$s";
    public static final String GENERATE_FILE_OUTPUT = DEBUG ? PATH_BASE : "/tmp/";
    public static final String GENERATE_ZIP_OUTPUT = DEBUG ? PATH_BASE  + "zip" + PATH_SEPERATOR : "/tmp/zip/";
    public static final String ZIP_SUFFIX = ".zip";
    public static final String THRIFT_SUFFIX = ".thrift";
}
