package com.dagger.sandbox;

import dagger.Component;

import javax.inject.Inject;

/**
 * Dependencies instantiated by dagger have their fields with @Inject automatically injected.
 */
class Example4 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample4_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1 localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
        printLocal(localDependency1.dependency3);
        printField(dependency1.dependency3);
    }

    @Component
    public interface SimpleComponent {
        void inject(Example4 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    static class Dependency1 {
        @Inject
        Dependency3 dependency3;

        @Inject
        Dependency1() {
        }
    }

    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }

    static class Dependency3 {
        @Inject
        Dependency3() {
        }
    }
}
