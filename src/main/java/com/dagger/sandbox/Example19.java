package com.dagger.sandbox;

import dagger.BindsOptionalOf;
import dagger.Component;
import dagger.Module;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Optional bindings.
 * If you want a binding to work even if some dependency is not bound in the component.
 * When would you use this? I have no idea!
 */
class Example19 extends BaseExample {

    @Inject
    Optional<Dependency1> dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample19_SimpleComponent.create();

        simpleComponent.inject(this);
        Optional<Dependency1> localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(Dependency1.class, localDependency1.isPresent() ? getHashMessage(localDependency1.get()) : "is null");
        printField(Dependency1.class, dependency1.isPresent() ? getHashMessage(dependency1.get()) : "is null");
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Singleton
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example19 example);

        Optional<Dependency1> dependency1();

        Dependency2 dependency2();
    }

    @Module
    static abstract class SimpleModule {
        @Singleton
        @BindsOptionalOf
        abstract Dependency1 provideDependency1();
    }

    static class Dependency1 {
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(Optional<Dependency1> dependency1) {
        }
    }
}
