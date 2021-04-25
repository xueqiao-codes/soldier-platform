package org.soldier.base.beanfactory.impl;

import org.soldier.base.beanfactory.BeanFactory;

public class InstanceHolderBeanFactoryBuilder implements BeanFactory.Builder {
    private Object mInstance;

    public InstanceHolderBeanFactoryBuilder(Object instance) {
        this.mInstance = instance;
    }

    @Override
    public Object build(Object... args) {
        return mInstance;
    }
}
