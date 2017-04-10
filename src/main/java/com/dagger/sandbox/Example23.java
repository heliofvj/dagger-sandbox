package com.dagger.sandbox;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Scope;
import javax.inject.Singleton;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.util.Map;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Bonus!
 * Here we use some of the cool features we've seen to build a sub-component map provider.
 */
class Example23 extends BaseExample {

    @Inject
    Map<Class<?>, Provider<ComponentBuilder>> componentBuilderProviderMap;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample23_SimpleComponent.create();
         simpleComponent.inject(this);

        SimpleSubComponentA simpleSubComponentA = getComponentBuilder(SimpleSubComponentA.class).build();
        SimpleSubComponentB simpleSubComponentB = getComponentBuilder(SimpleSubComponentB.class).build();

        printSelf();
        for (int i = 0; i < 2; i++) {
            Dependency1 localDependency1A = simpleSubComponentA.dependency1();
            Dependency1 localDependency1B = simpleSubComponentB.dependency1();
            printLocal(localDependency1A);
            printLocal(localDependency1B);
        }
    }

    interface ComponentBuilder<Comp> {
        Comp build();
    }

    private <Comp> ComponentBuilder<Comp> getComponentBuilder(Class<Comp> componentClass) {
        return componentBuilderProviderMap.get(componentClass).get();
    }

    @Singleton
    @Component(modules = {SubComponentsModule.class})
    public interface SimpleComponent {
        void inject(Example23 example23);
    }

    @Module(subcomponents = {SimpleSubComponentA.class, SimpleSubComponentB.class})
    static abstract class SubComponentsModule {
        @Binds
        @IntoMap
        @ClassKey(SimpleSubComponentA.class)
        abstract ComponentBuilder bindSimpleSubComponentABuilder(SimpleSubComponentA.Builder builder);

        @Binds
        @IntoMap
        @ClassKey(SimpleSubComponentB.class)
        abstract ComponentBuilder bindSimpleSubComponentBBuilder(SimpleSubComponentB.Builder builder);
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
        interface Builder extends ComponentBuilder<SimpleSubComponentA> {
        }
    }

    @MySubScope
    @Subcomponent
    public interface SimpleSubComponentB {
        Dependency1 dependency1();

        @Subcomponent.Builder
        interface Builder extends ComponentBuilder<SimpleSubComponentB> {
        }
    }

    @MySubScope
    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }
}