package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Qualifiers.
 * Sometimes the type alone is insufficient to identify a dependency.
 * In this case, we add a qualifier annotation.
 * This is any annotation that itself has a @Qualifier annotation, like @Named.
 */
class Example18 extends BaseExample {

    @Inject
    @Named("SomeKindOfDependency1")
    Dependency1 someKindOfDependency1;
    @Inject
    @Named("AnotherKindOfDependency1")
    Dependency1 anotherKindOfDependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample18_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1 localSomeKindOfDependency1 = simpleComponent.someKindOfDependency1();
        Dependency1 localAnotherKindOfDependency1 = simpleComponent.anotherKindOfDependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(someKindOfDependency1, "of some kind");
        printField(localSomeKindOfDependency1, "of some kind");
        printLocal(anotherKindOfDependency1, "of another kind");
        printField(localAnotherKindOfDependency1, "of another kind");
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Singleton
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example18 example);

        @Named("SomeKindOfDependency1")
        Dependency1 someKindOfDependency1();

        @Named("AnotherKindOfDependency1")
        Dependency1 anotherKindOfDependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        @Singleton
        @Named("SomeKindOfDependency1")
        static Dependency1 provideSomeKindOfDependency1() {
            return new Dependency1();
        }

        @Provides
        @Named("AnotherKindOfDependency1")
        static Dependency1 provideAnotherKindOfDependency1() {
            return new Dependency1();
        }
    }

    static class Dependency1 {
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(@Named("AnotherKindOfDependency1") Dependency1 dependency1) {
        }
    }
}
