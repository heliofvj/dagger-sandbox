package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Most basic example: a component and a module.
 * The module provides dependencies to the module.
 * The component makes these dependencies available through its interface.
 */
class Example01 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample01_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1 localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example01 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }

        @Provides
        static Dependency2 provideDependency2(Dependency1 dependency1) {
            return new Dependency2(dependency1);
        }
    }

    static class Dependency1 {
    }

    static class Dependency2 {
        Dependency2(Dependency1 dependency1) {
        }
    }
}
