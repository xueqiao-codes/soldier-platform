package org.soldier.base.beanfactory.impl;


import java.util.Arrays;
import java.util.Collection;

import org.soldier.base.beanfactory.BeanFactory;

public class BeanFactoryCollection implements BeanFactory {
    private Collection <? extends BeanFactory > mBeanFactories;

    public BeanFactoryCollection(BeanFactory... args) {
        mBeanFactories = Arrays.asList(args);
    }

    public BeanFactoryCollection(Collection <? extends BeanFactory > beanFactories) {
        mBeanFactories = beanFactories;
    }

    @Override
    public <T> T queryInterface(Class<T> clazz, Object... args) {
        for (BeanFactory beanFactory : mBeanFactories) {
            T t = beanFactory.queryInterface(clazz, args);
            if (t != null)
                return t;
        }
        return null;
    }
}
