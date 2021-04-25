package org.soldier.platform.web_framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.soldier.base.logger.AppLog;

public class CMethodController extends CController {

	@Override
	protected void process() throws Exception {
		String methodName = getPath(1);
		if (methodName == null) {
			show404("Method controller but not set method name!");
			return ;
		}
		try {
			handleMethod(methodName);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	protected <T extends CMethodController> void forward(Class<T> controllerClazz, 
			String methodName) throws Throwable {
		AppLog.d("forward " + controllerClazz.getSimpleName() + "/" + methodName);
		
		CMethodController controller = controllerClazz.newInstance();
		transfer(controller);
		forwardTo(controller);
		controller.handleMethod(methodName);
	}
	
	protected void forwardTo(CMethodController target) {
	}
	
	protected void beforeMethod(String methodName) throws Throwable {
	}
	
	protected void afterMethod(String methodName) throws Throwable {
	}
	
	private void handleMethod(String methodName) throws Throwable {
		Method method = null;
		try {
			method = getClass().getMethod(methodName);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			show404();
			return ;
		}
		
		beforeMethod(methodName);
		try {
			method.invoke(this);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			afterMethod(methodName);
		}
	}

}
