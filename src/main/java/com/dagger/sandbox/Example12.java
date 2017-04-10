package com.dagger.sandbox;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * You can install sub-components in two other ways:
 * <p>
 * In the component interface: declaring a sub-component builder instead of the actual sub-component.
 * Useful when your sub-component builder requires something that the parent component can't inject.
 * <p>
 * In a module: you can declare the sub-component classes in a module attached to the parent.
 * This removes the sub-component declarations from the component interface.
 * Unfortunately, it also means you cant directly get a instance of the sub-component through the component interface.
 * Instead, you need to inject the sub-component builder in a class field.
 * When you use Module.subcomponents, all those sub-components need to declare a @Subcomponent.Builder interface.
 * And you can't inject the sub-components themselves, only their builders.
 * This is the preferred way of installing sub-components.
 */
class Example12 extends BaseExample {

    @Inject
    SimpleSubComponentA.Builder simpleSubComponentABuilder;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample12_SimpleComponent.create();
        simpleComponent.inject(this);
        SimpleSubComponentA simpleSubComponentA = simpleSubComponentABuilder.build();
        SimpleSubComponentB simpleSubComponentB = simpleComponent.simpleSubComponentBBuilder()
                .somethingParentCantInject(new Object())
                .build();

        printSelf();
        for (int i = 0; i < 2; i++) {
            Dependency1 localDependency1A = simpleSubComponentA.dependency1();
            Dependency1 localDependency1B = simpleSubComponentB.dependency1();
            printLocal(localDependency1A);
            printLocal(localDependency1B);
        }
    }

    @Singleton
    @Component(modules = {SubComponentsModule.class})
    public interface SimpleComponent {
        void inject(Example12 example12);

        SimpleSubComponentB.Builder simpleSubComponentBBuilder();
    }

    @Module(subcomponents = {SimpleSubComponentA.class})
    static class SubComponentsModule {
    }

    @Documented
    @Retention(RUNTIME)
    @Scope
    @interface MySubScope {
    }

    @MySubScope
    @Subcomponent
    public interface SimpleSubComponentA {
        Dependency1 dependency1();

        @Subcomponent.Builder
        interface Builder {
            SimpleSubComponentA build();
        }
    }

    @MySubScope
    @Subcomponent
    public interface SimpleSubComponentB {
        Dependency1 dependency1();

        @Subcomponent.Builder
        interface Builder {
            @BindsInstance
            Builder somethingParentCantInject(Object object);

            SimpleSubComponentB build();
        }
    }

    @MySubScope
    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }
}
