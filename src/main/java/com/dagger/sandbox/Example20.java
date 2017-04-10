package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

/**
 * IntoSet and ElementsIntoSet.
 * Dagger allows you to bind several objects into a collection even when the objects are bound in different modules
 * To add an element to an injectable multibound set, add an IntoSet/ElementsIntoSet annotation to your module method.
 */
class Example20 extends BaseExample {

    @Inject
    Set<Dependency1> dependencies;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample20_SimpleComponent.create();

        simpleComponent.inject(this);
        Set<Dependency1> localDependencies = simpleComponent.dependency1s();

        printSelf();
        for (Dependency1 dependency1 : dependencies) {
            printField(dependency1);
        }
        printDivider();
        for (Dependency1 dependency1 : localDependencies) {
            printLocal(dependency1);
        }
    }

    @Singleton
    @Component(modules = {SimpleModuleA.class, SimpleModuleB.class})
    public interface SimpleComponent {
        void inject(Example20 example);

        Set<Dependency1> dependency1s();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModuleA {
        @Provides
        @Singleton
        @IntoSet
        static Dependency1 provideDependency11() {
            return new Dependency11();
        }

        @Provides
        @IntoSet
        static Dependency1 provideDependency12() {
            return new Dependency12();
        }
    }

    @Module
    static class SimpleModuleB {
        @Provides
        @Singleton
        @ElementsIntoSet
        static Set<Dependency1> provideDependencies13() {
            Set<Dependency1> dependencies = new HashSet<>();
            for (int i = 0; i < 2; i++) {
                dependencies.add(new Dependency13());
            }
            return dependencies;
        }

        @Provides
        @ElementsIntoSet
        static Set<Dependency1> provideDependencies14() {
            Set<Dependency1> dependency1s = new HashSet<>();
            for (int i = 0; i < 2; i++) {
                dependency1s.add(new Dependency14());
            }
            return dependency1s;
        }
    }

    static class Dependency1 {
    }

    static class Dependency11 extends Dependency1 {
    }

    static class Dependency12 extends Dependency1 {
    }

    static class Dependency13 extends Dependency1 {
    }

    static class Dependency14 extends Dependency1 {
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(Set<Dependency1> dependency1s) {
        }
    }
}
