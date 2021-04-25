package org.soldier.base.beanfactory.impl;

import java.util.HashMap;
import java.util.Map;

import org.soldier.base.beanfactory.BeanFactory;

public class BeanFactoryImpl implements BeanFactory {
    private Map < Class<?>, Builder > mBeanFactories = new HashMap < Class<?>, Builder > ();

    @Override
    public <T> T queryInterface(Class<T> clazz, Object... args) {
        Builder builder = mBeanFactories.get(clazz);

        return (builder != null) ? clazz.cast(builder.build(args)) : null;
    }

    public void addImplements(Object... objects) {
        for (Object object : objects) {
            if (object instanceof Class<?>) {
                addOneImplementClass((Class<?>) object);
            } else {
                addOneImplementInstance(object);
            }
        }
    }

    public void registerInterfaces(Class<?> clazz, Builder beanFactory) {
        Class<?> c = clazz;

        if (clazz.isInterface()) {
            mBeanFactories.put(clazz, beanFactory);
        }

        while ((c != null) && !c.equals(Object.class)) {
            for (Class<?> interfaze : c.getInterfaces()) {
                mBeanFactories.put(interfaze, beanFactory);
            }

            c = c.getSuperclass();
        }
    }

    private void addOneImplementInstance(Object object) {
        Builder beanFactory = new InstanceHolderBeanFactoryBuilder(object);
        registerInterfaces(object.getClass(), beanFactory);
    }

    private void addOneImplementClass(Class<?> clazz) {
        Builder beanFactory = new SingletonBeanFactoryBuilder(new NewInstanceBeanFactoryBuilder(
                    clazz));
        registerInterfaces(clazz, beanFactory);
    }
}
