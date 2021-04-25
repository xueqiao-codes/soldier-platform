package org.soldier.base.beanfactory.impl;

import org.soldier.base.beanfactory.BeanFactory;

public class BeanFactoryWrapper implements BeanFactory {
    private BeanFactory mBeanFactory;

    public BeanFactoryWrapper(Object beanFactory) {
        mBeanFactory = beanFactory instanceof BeanFactory ? (BeanFactory) beanFactory : new NullBeanFactory();
    }

    public BeanFactoryWrapper(BeanFactory beanFactory) {
        this.mBeanFactory = beanFactory;
    }

    @Override
    public <T> T queryInterface(Class<T> clazz, Object... args) {
        return mBeanFactory.queryInterface(clazz, args);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.mBeanFactory = beanFactory;
    }
}
