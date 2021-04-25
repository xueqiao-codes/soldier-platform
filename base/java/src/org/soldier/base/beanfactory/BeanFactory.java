package org.soldier.base.beanfactory;

public interface BeanFactory {
    /**
     * BeanFactory was inspired by <a href="http://picocontainer.codehaus.org/">PicoContainer</a>,
     * which is an Inversion of Control (IoC) container for components honor the Dependency Injection pattern.
     *
     * The basic idea is, BeanFactory is a container we can query an instance from by its class type,
     * regardless of how this one should be built. Which means, the instance we queried from might
     * be a singleton or being created everytime when we query. We just don't care.
     * All the instance initialization mechanism is up to its container's implementation.
     *
     * @param clazz Given class type used to query an instance from.
     * @param args Arguments needed to create an instance.
     * @return Object which is an instance of type clazz, or null if not one found.
     */
    public <T> T queryInterface(Class<T> clazz, Object... args);

    public interface Builder {
        public Object build(Object... args);
    }
}
