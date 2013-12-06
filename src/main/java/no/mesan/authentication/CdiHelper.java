package no.mesan.authentication;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

class CdiHelper {
    public static <T> void programmaticInjection(final Class<T> clazz, final T injectionObject) {
        try {
            final InitialContext initialContext;
            initialContext = new InitialContext();
            final Object lookup = initialContext.lookup("java:comp/BeanManager");
            final BeanManager beanManager = (BeanManager) lookup;
            final AnnotatedType<T> annotatedType = beanManager.createAnnotatedType(clazz);
            final InjectionTarget<T> injectionTarget = beanManager.createInjectionTarget(annotatedType);
            final CreationalContext<T> creationalContext = beanManager.createCreationalContext(null);
            injectionTarget.inject(injectionObject, creationalContext);
            creationalContext.release();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
