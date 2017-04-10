package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Dependencies can be automatically constructed made available using @Inject on constructors.
 */
class Example2 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample2_SimpleComponent.create();

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
        void inject(Example2 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }
    }

    static class Dependency1 {
    }

    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
