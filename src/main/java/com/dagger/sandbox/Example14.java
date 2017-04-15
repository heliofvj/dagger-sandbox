package com.dagger.sandbox;

import dagger.*;

import javax.inject.Singleton;

/**
 * Reusable.
 * Bindings with this scope are not associated with any single component.
 * Each component that actually uses the binding will cache the returned or instantiated object.
 * Very useful when you want to limit the number of instances but don’t need to guarantee a single instance.
 */
class Example14 extends BaseExample {

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample14_SimpleComponent.create();
        SimpleSubComponentA simpleSubComponentA = simpleComponent.newSimpleSubComponentA();
        SimpleSubComponentB simpleSubComponentB = simpleComponent.newSimpleSubComponentB();

        printSelf();
        for (int i = 0; i < 2; i++) {
            Dependency1 localDependency1A = simpleSubComponentA.dependency1();
            Dependency2 localDependency2A = simpleSubComponentA.dependency2();
            Dependency1 localDependency1B = simpleSubComponentB.dependency1();
            Dependency2 localDependency2B = simpleSubComponentB.dependency2();
            printLocal(localDependency1A, "SubComponentA");
            printLocal(localDependency2A, "SubComponentA");
            printLocal(localDependency1B, "SubComponentB");
            printLocal(localDependency2B, "SubComponentB");
            printDivider();
        }
    }

    @Singleton
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        SimpleSubComponentA newSimpleSubComponentA();

        SimpleSubComponentB newSimpleSubComponentB();
    }

    @Module
    static class SimpleModule {
        @Provides
        @Singleton
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }

        @Provides
        @Reusable
        static Dependency2 provideDependency2(Dependency1 dependency1) {
            return new Dependency2(dependency1);
        }
    }

    @Subcomponent
    public interface SimpleSubComponentA {
        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Subcomponent
    public interface SimpleSubComponentB {
        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    static class Dependency1 {
    }

    static class Dependency2 {
        Dependency2(Dependency1 dependency1) {
        }
    }
}
