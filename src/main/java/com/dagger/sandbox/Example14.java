package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Reusable.
 * Bindings with this scope are not associated with any single component.
 * Each component that actually uses the binding will cache the returned or instantiated object.
 * Very useful when you want to limit the number of instances but don’t need to guarantee a single instance.
 */
class Example14 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample14_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1 localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Singleton
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example14 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        @Reusable
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }
    }

    static class Dependency1 {
    }

    @Reusable
    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
