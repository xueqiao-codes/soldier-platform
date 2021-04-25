package org.soldier.base.beanfactory.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.soldier.base.beanfactory.BeanFactory;


public class NewInstanceBeanFactoryBuilder implements BeanFactory.Builder {
    private Class<?> mClass;

    public NewInstanceBeanFactoryBuilder(Class<?> clazz) {
        this.mClass = clazz;
    }

    @Override
    public Object build(Object... args) {
        Constructor<?> constructor = findSuitableConstructor(mClass, args);

        if (constructor != null) {
            try {
                return constructor.newInstance(args);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }

        return null;
    }

    private Constructor<?> findSuitableConstructor(Class<?> clazz,
            Object... args) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers())) {
                Class<?>[] paramTypes = constructor.getParameterTypes();

                if (args.length == paramTypes.length) {
                    Object[] params = typeCast(paramTypes, args);

                    if (params != null) {
                        return constructor;
                    }
                }
            }
        }

        return null;
    }

    private static Object[] typeCast(Class<?>[] paramTypes, Object[] args) {
        Object[] params = new Object[args.length];
        int i;

        for (i = 0; i < args.length; i++) {
            if ((args[i] == null) ||
                    paramTypes[i].isAssignableFrom(args[i].getClass())) {
                params[i] = args[i];
            } else {
                return null;
            }
        }

        return params;
    }
}
