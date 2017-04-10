package com.dagger.sandbox;

import dagger.Binds;
import dagger.Component;
import dagger.Module;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Binds.
 * Can be used to provide implementations of interfaces and abstract classes or provide subclasses as superclasses.
 * Requires dependencies that can be injected automatically.
 * Easier than a provider, since you don't need to instantiate the dependency, nor declare and update method params.
 */
class Example22 extends BaseExample {

    @Inject
    Dependency1Abstract dependency1Abstract;
    @Inject
    Dependency2Interface dependency2Interface;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample22_SimpleComponent.create();

        simpleComponent.inject(this);
        Dependency1Abstract localDependency1Abstract = simpleComponent.dependency1Abstract();
        Dependency2Interface localDependency2Interface = simpleComponent.dependency2Interface();

        printSelf();
        printLocal(localDependency1Abstract);
        printField(dependency1Abstract);
        printLocal(localDependency2Interface);
        printField(dependency2Interface);
    }

    @Singleton
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example22 example);

        Dependency1Abstract dependency1Abstract();

        Dependency2Interface dependency2Interface();
    }

    @Module
    static abstract class SimpleModule {
        @Binds
        abstract Dependency1Abstract bindsDependency1(Dependency1 dependency1);

        @Binds
        @Singleton
        abstract Dependency2Interface bindsDependency2(Dependency2 dependency2);
    }

    static abstract class Dependency1Abstract {
    }

    @Singleton
    static class Dependency1 extends Dependency1Abstract {
        @Inject
        Dependency1() {
        }
    }

    interface Dependency2Interface {
    }

    static class Dependency2 implements Dependency2Interface {
        @Inject
        Dependency2(Dependency1Abstract dependency1Abstract) {
        }
    }
}