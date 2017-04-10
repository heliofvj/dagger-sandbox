package com.dagger.sandbox;

import dagger.Component;
import dagger.Lazy;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Lazy injections.
 * When you know you need an object, but doesn't know exactly when.
 */
class Example16 extends BaseExample {

    @Inject
    Lazy<Dependency1> dependency1Lazy;
    @Inject
    Lazy<Dependency2> dependency2Lazy;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample16_SimpleComponent.create();

        simpleComponent.inject(this);
        Lazy<Dependency1> localDependency1Lazy = simpleComponent.dependency1Lazy();
        Lazy<Dependency2> localDependency2Lazy = simpleComponent.dependency2Lazy();

        printSelf();
        printLocal(localDependency1Lazy);
        printLocal(localDependency1Lazy.get());
        printLocal(localDependency1Lazy.get());
        printField(dependency1Lazy);
        printField(dependency1Lazy.get());
        printField(dependency1Lazy.get());
        printDivider();
        printLocal(localDependency2Lazy);
        printLocal(localDependency2Lazy.get());
        printLocal(localDependency2Lazy.get());
        printField(dependency2Lazy);
        printField(dependency2Lazy.get());
        printField(dependency2Lazy.get());
    }

    @Singleton
    @Component
    public interface SimpleComponent {
        void inject(Example16 example);

        Lazy<Dependency1> dependency1Lazy();

        Lazy<Dependency2> dependency2Lazy();
    }

    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
