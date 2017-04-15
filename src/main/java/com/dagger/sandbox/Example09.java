package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Sub-scopes.
 * Of course you can use scoped bindings in sub-components!
 * Root components generally use the @Singleton scope.
 * For sub-components you need to create new scope annotations.
 */
class Example09 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample09_SimpleComponent.create();
        SimpleSubComponent simpleSubComponent = simpleComponent.newSimpleSubComponent();

        simpleSubComponent.inject(this);
        Dependency1 localDependency1 = simpleSubComponent.dependency1();
        Dependency2 localDependency2 = simpleSubComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Singleton
    @Component(modules = {SimpleModuleA.class})
    public interface SimpleComponent {
        SimpleSubComponent newSimpleSubComponent();
    }

    @Module
    static class SimpleModuleA {
        @Provides
        @Singleton
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }
    }

    @Documented
    @Retention(RUNTIME)
    @Scope
    @interface MySubScope {
    }

    @MySubScope
    @Subcomponent(modules = {SimpleModuleB.class})
    public interface SimpleSubComponent {
        void inject(Example09 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModuleB {
        @Provides
        @MySubScope
        static Dependency2 provideDependency2(Dependency1 dependency1) {
            return new Dependency2(dependency1);
        }
    }

    static class Dependency1 {
        Dependency1() {
        }
    }

    static class Dependency2 {
        Dependency2(Dependency1 dependency1) {
        }
    }
}
