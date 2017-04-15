package com.dagger.sandbox;

import dagger.Component;

import javax.inject.Inject;

/**
 * No need for a module when all dependencies can be provided automatically!
 */
class Example03 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample03_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1 localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Component
    public interface SimpleComponent {
        void inject(Example03 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }

    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
