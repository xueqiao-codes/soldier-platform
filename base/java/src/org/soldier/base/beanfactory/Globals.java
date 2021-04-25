package org.soldier.base.beanfactory;

import org.soldier.base.beanfactory.impl.BeanFactoryImpl;

public class Globals extends BeanFactoryImpl {
	private static  Globals sInstance;

    private Globals() {
    }

    static public synchronized Globals getInstance() {
    	if (sInstance == null) {
    		synchronized (Globals.class) {
				if (sInstance == null) {
					sInstance = new Globals();
				}
			}
    	}
    	return sInstance;
    }

    public <T> T queryInterfaceForSure(Class<T> clazz, Object... args) {
        T inf = super.queryInterface(clazz, args);

        if (inf == null) {
            throw new BeanNotFoundException(clazz.getName());
        }

        return inf;
    }
}
