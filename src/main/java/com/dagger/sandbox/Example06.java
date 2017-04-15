package com.dagger.sandbox;

import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Inject;

/**
 * You can add @BindsInstance methods to component builders to allow instances to be directly injected in the components.
 * This should be preferred to writing a @Module with constructor arguments and immediately providing those values.
 */
class Example06 extends BaseExample implements Dependency1Interface {

    @Inject
    Dependency1Interface dependency1Interface;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample06_SimpleComponent.builder()
                .dependency1(this)
                .build();

        simpleComponent.inject(this);
        Dependency1Interface localDependency1Interface = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printSelf();
        printLocal(localDependency1Interface);
        printField(dependency1Interface);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Component
    public interface SimpleComponent {
        void inject(Example06 example);

        Dependency1Interface dependency1();

        Dependency2 dependency2();

        @Component.Builder
        interface Builder {
            @BindsInstance
            Builder dependency1(Dependency1Interface dependency1Interface);

            SimpleComponent build();
        }
    }

    static class Dependency2 {
        @Inject
        Dependency2(Dependency1Interface dependency1Interface) {
        }
    }
}
