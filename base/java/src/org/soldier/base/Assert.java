package org.soldier.base;

public class Assert {
	private static org.apache.log4j.Logger log4jLogger = null;
	private static org.slf4j.Logger slf4jLogger = null;
	private static boolean isOpen = true;
	
	public static void setOpen(boolean open) {
		isOpen = open;
	}
	
	public static void SetLogger(final org.apache.log4j.Logger logger){
		Assert.log4jLogger = logger;
	}
	
	public static void SetLogger(final org.slf4j.Logger logger){
		Assert.slf4jLogger = logger;
	}
	
	public static void True(final boolean result){
		Assert.True(result, "Assertion Failed");
	}
	
	public static void True(final boolean result, final String msg){
		if(!result && isOpen){
			PrintStackTrace();
			throw new Error(msg);
		}
	}
	
	public static void Equals(final Object source, 
							final Object target){
		Assert.True(source.equals(target));
	}
	
	public static void Equals(final Object source, 
							final Object target, final String msg){
		Assert.True(source.equals(target), msg);
	}
	
	public static void NotNull(final Object source){
		Assert.True(source != null);
	}
	
	public static void NotNull(final Object source, final String msg){
		Assert.True(source != null, msg);
	}
	
	private static void PrintStackTrace(){
		if(log4jLogger != null){
			StackTrace.PrintStackTrace2Logger(new Exception(), log4jLogger);
		}else if(slf4jLogger != null){
			StackTrace.PrintStackTrace2Logger(new Exception(), slf4jLogger);
		}else{
			new Exception().printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		Assert.setOpen(false);
		Assert.True(false);
		System.out.println("Assert is not open");
	}
}
