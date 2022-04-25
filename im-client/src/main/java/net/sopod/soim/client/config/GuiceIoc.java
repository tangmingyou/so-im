package net.sopod.soim.client.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.Set;

/**
 * Ioc
 *
 * @author tmy
 * @date 2022-04-25 11:02
 */
public class GuiceIoc extends AbstractModule {

    private final String rootPackage;

    private Set<Class<?>> beanTypes = Collections.emptySet();

    /**
     * @param rootClass 从该类所在包开始扫描
     */
    public GuiceIoc(Class<?> rootClass) {
        this.rootPackage = rootClass.getPackage().getName();
        this.scanRootPackage();
    }

    /**
     * guice 注册 bean
     */
    @Override
    protected void configure() {
        for (Class<?> beanType : this.beanTypes) {
            bind(beanType);
        }
        // 注册后释放内存
        this.beanTypes = Collections.emptySet();
    }

    /**
     * 扫描需要注入的类，带 {@link Singleton} 注解的类
     */
    private void scanRootPackage() {
        Reflections reflect = new Reflections(rootPackage);
        this.beanTypes = reflect.getTypesAnnotatedWith(Singleton.class);
    }

}
