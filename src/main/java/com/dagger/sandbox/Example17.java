package com.dagger.sandbox;

import dagger.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Provider injections.
 * When you need many objects of the same type. Like a factory or pool.
 */
class Example17 extends BaseExample {

    @Inject
    Provider<Dependency1> dependency1Provider;
    @Inject
    Provider<Dependency2> dependency2Provider;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample17_SimpleComponent.create();

        simpleComponent.inject(this);
        Provider<Dependency1> localDependency1Provider = simpleComponent.dependency1Provider();
        Provider<Dependency2> localDependency2Provider = simpleComponent.dependency2Provider();

        printSelf();
        printLocal(localDependency1Provider);
        printLocal(localDependency1Provider.get());
        printLocal(localDependency1Provider.get());
        printField(dependency1Provider);
        printField(dependency1Provider.get());
        printField(dependency1Provider.get());
        printDivider();
        printLocal(localDependency2Provider);
        printLocal(localDependency2Provider.get());
        printLocal(localDependency2Provider.get());
        printField(dependency2Provider);
        printField(dependency2Provider.get());
        printField(dependency2Provider.get());
    }

    @Singleton
    @Component
    public interface SimpleComponent {
        void inject(Example17 example);

        Provider<Dependency1> dependency1Provider();

        Provider<Dependency2> dependency2Provider();
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
