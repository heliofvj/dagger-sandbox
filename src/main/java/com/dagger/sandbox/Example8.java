package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Sub-components.
 * These components inherit and extend the object graph of a parent component.
 * Annotate sub-components with the @Subcomponent annotation.
 * You add modules to it just like any component.
 * To build and attach the sub-component to a parent component, you declare it explicitly in the component interface.
 * Every time you use the interface you'll get a new component, so keep the instance after you call it.
 */
class Example8 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample8_SimpleComponent.create();
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

    @Subcomponent(modules = {SimpleModuleB.class})
    public interface SimpleSubComponent {
        void inject(Example8 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModuleB {
        @Provides
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
