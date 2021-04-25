package org.soldier.base.beanfactory;

public class BeanNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BeanNotFoundException(String detailMessage) {
        super(detailMessage);
    }
}