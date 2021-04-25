package org.soldier.platform.thriftgen.util;


import org.soldier.base.logger.AppLog;

import java.io.*;

public class ShellExecutor {

    public static class ExecResult {
        private int retCode;
        private String retString;

        public boolean isSuccess() {
            return retCode == 0;
        }

        public String getRetString() {
            return retString;
        }

        public int getRetCode() {
            return retCode;
        }

        public ExecResult(int retCode, String retString) {
            this.retCode = retCode;
            this.retString = retString;
        }
    }

    public static ExecResult execCmd(String cmd) {
        AppLog.i("execCmd " + cmd);

        Runtime run = Runtime.getRuntime();
        StringBuffer error = new StringBuffer();
        try {
            Process p = run.exec(cmd);
            p.waitFor();
            int exitValue = p.exitValue();
            if (exitValue != 0) {
                error.append(getStringFromInputStream(p.getErrorStream()));
            } else {
                error.append(getStringFromInputStream(p.getInputStream()));
            }
            return new ExecResult(p.exitValue(), error.toString());
        } catch (IOException e) {
            error.append(e);
        } catch (InterruptedException e) {
            error.append(e);
        }
        return new ExecResult(-1, error.toString());
    }

    private static String getStringFromInputStream(InputStream is) {
        String result = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                result += lineStr;
            }
        } catch (Exception e) {
            AppLog.e(e);
        } finally {
            FileUtil.safelyClose(br);
        }
        return result;
    }
}
