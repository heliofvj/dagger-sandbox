package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Singleton.
 * This annotation is a binding scope.
 * In a scoped component, the bindings (@Provides methods, @Inject constructors) annotated with the component scope
 * will only be called once.
 * The component will always return the same instance for scoped dependencies.
 */
class Example07 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample07_SimpleComponent.create();

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
        void inject(Example07 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        @Singleton
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }
    }

    static class Dependency1 {
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
