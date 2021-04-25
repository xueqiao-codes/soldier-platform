package org.soldier.base.logger;

import java.io.PrintStream;

public class AppLogStream extends PrintStream {
	private static AppLogStream outInstance = new AppLogStream(System.out, false);
	private static AppLogStream errInstance = new AppLogStream(System.err, true);
	
	private boolean isError;
	private PrintStream originalOut;
	private boolean isUseOrignalOut;
	private AppLogStream(PrintStream out, boolean isError) {  
        super(out);  
        originalOut = out;
        this.isError = isError;
        this.isUseOrignalOut = false;
    }  
  
    public static void redirectSystemPrint() {  
    	outInstance.isUseOrignalOut = false;
    	errInstance.isUseOrignalOut = false;
        System.setOut(outInstance);  
        System.setErr(errInstance);
    }
    
    public static void restoreSystemPrint() {
    	outInstance.isUseOrignalOut = true;
    	errInstance.isUseOrignalOut = true;
    }
    
    private void appLogPrintln(Object message) {
    	if (isUseOrignalOut) {
    		originalOut.println(message.toString());
    		return ;
    	} 
    	
    	if (isError) {
    		AppLog.e(message);
    	} else {
    		AppLog.d(message);
    	}
    }
  
    public void print(boolean b) {  
        println(b);  
    }  
  
    public void print(char c) {  
        println(c);  
    }  
  
    public void print(char[] s) {  
        println(s);  
    }  
  
    public void print(double d) {  
        println(d);  
    }  
  
    public void print(float f) {  
        println(f);  
    }  
  
    public void print(int i) {  
        println(i);  
    }  
  
    public void print(long l) {  
        println(l);  
    }  
  
    public void print(Object obj) {  
        println(obj);  
    }  
  
    public void print(String s) {  
        println(s);  
    }  
  
    public void println(boolean x) {  
    	appLogPrintln(Boolean.valueOf(x));  
    }  
  
    public void println(char x) {  
    	appLogPrintln(Character.valueOf(x));  
    }  
  
    public void println(char[] x) {  
    	appLogPrintln(x == null ? null : new String(x));  
    }  
  
    public void println(double x) {  
    	appLogPrintln(Double.valueOf(x));  
    }  
  
    public void println(float x) {  
    	appLogPrintln(Float.valueOf(x));  
    }  
  
    public void println(int x) {  
    	appLogPrintln(Integer.valueOf(x));  
    }  
  
    public void println(long x) {  
    	appLogPrintln(x);  
    }  
  
    public void println(Object x) {  
    	appLogPrintln(x);  
    }  
  
    public void println(String x) {  
    	appLogPrintln(x);  
    } 

}
