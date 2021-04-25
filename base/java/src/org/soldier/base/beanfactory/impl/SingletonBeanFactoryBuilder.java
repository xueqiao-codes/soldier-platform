package org.soldier.base.beanfactory.impl;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.soldier.base.beanfactory.BeanFactory;

public class SingletonBeanFactoryBuilder implements BeanFactory.Builder {
    private BeanFactory.Builder mBuilder;
    private Map<ArrayObjectKey, Object> mInstances = new HashMap<ArrayObjectKey, Object>();

    public SingletonBeanFactoryBuilder(BeanFactory.Builder builder) {
        this.mBuilder = builder;
    }

    @Override
    public Object build(Object... args) {
        ArrayObjectKey key = new ArrayObjectKey(args);
        Object value = mInstances.get(key);

        if (value == null) {
            value = mBuilder.build(args);
            mInstances.put(key, value);
        }

        return value;
    }

    private static class ArrayObjectKey {
        private Object[] mObjects;

        public ArrayObjectKey(Object[] objects) {
            this.mObjects = objects;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }

            ArrayObjectKey that = (ArrayObjectKey) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(mObjects, that.mObjects)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return (mObjects != null) ? Arrays.hashCode(mObjects) : 0;
        }
    }
}
