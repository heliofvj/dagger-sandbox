package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Often you only have data available at the time you’re building a component.
 * You can give this data to a module through its constructor.
 * Then the module can provide the data to the component.
 * All modules with a non default constructor needs to be instantiated manually.
 */
class Example5 extends BaseExample implements Dependency1Interface {

    @Inject
    Dependency1Interface dependency1Interface;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample5_SimpleComponent.builder()
                .simpleModule(new SimpleModule(this))
                .build();

        simpleComponent.inject(this);
        Dependency1Interface localDependency1Interface = simpleComponent.dependency1Interface();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1Interface);
        printField(dependency1Interface);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example5 example);

        Dependency1Interface dependency1Interface();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {

        private Dependency1Interface dependency1Interface;

        SimpleModule(Dependency1Interface dependency1Interface) {
            this.dependency1Interface = dependency1Interface;
        }

        @Provides
        Dependency1Interface provideDependency1Interface() {
            return dependency1Interface;
        }
    }

    static class Dependency2 {
        @Inject
        Dependency2(Dependency1Interface dependency1Interface) {
        }
    }
}
