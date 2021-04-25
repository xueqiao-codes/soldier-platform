package org.soldier.base;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StackTrace {
	/**
	 * 打印异常抛出栈到对于的log4j的logger中
	 * 
	 * @param exception
	 * @param logger
	 */
	public static void PrintStackTrace2Logger(final Exception exception,
			org.apache.log4j.Logger logger) {
		ByteArrayOutputStream eMemStream = new ByteArrayOutputStream(4096);
		PrintStream pStream = new PrintStream(eMemStream);
		try {
		    exception.printStackTrace(new PrintStream(eMemStream));
		    logger.error(eMemStream.toString());
		} finally {
		    pStream.close();
		}
	}

	/**
	 * 打印异常抛出栈到对于的slf4j的logger中
	 * 
	 * @param exception
	 * @param logger
	 */
	public static void PrintStackTrace2Logger(final Exception exception,
			org.slf4j.Logger logger) {
		ByteArrayOutputStream eMemStream = new ByteArrayOutputStream(4096);
		PrintStream pStream = new PrintStream(eMemStream);
		try {
		    exception.printStackTrace();
		    logger.error(eMemStream.toString());
		} finally {
		    pStream.close();
		}
	}
	
	public static String toString(final Exception exception) {
	    ByteArrayOutputStream eMemStream = new ByteArrayOutputStream(1024);
        PrintStream pStream = new PrintStream(eMemStream);
        try {
            exception.printStackTrace(new PrintStream(eMemStream));
            return eMemStream.toString();
        } finally {
            pStream.close();
        }
	}
}
