package org.soldier.base.beanfactory.impl;

import org.soldier.base.beanfactory.BeanFactory;

public class NullBeanFactory implements BeanFactory {
    @Override
    public <T> T queryInterface(Class<T> clazz, Object... args) {
        return null;
    }
}
