package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * IntoMap.
 * Dagger allows you to add elements to an injectable map as long as the map keys are known at compile time.
 * Create a binding that is annotated with @IntoMap and another annotation that specifies the map key for that entry.
 */
class Example21 extends BaseExample {

    @Inject
    Map<String, Dependency1> dependencies;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample21_SimpleComponent.create();

        simpleComponent.inject(this);
        Map<String, Dependency1> localDependencies = simpleComponent.dependency1s();

        printSelf();
        for (Map.Entry<String, Dependency1> entry : dependencies.entrySet()) {
            printField(entry.getValue(), entry.getKey());
        }
        for (Map.Entry<String, Dependency1> entry : localDependencies.entrySet()) {
            printLocal(entry.getValue(), entry.getKey());
        }
    }

    @Singleton
    @Component(modules = {SimpleModule.class})
    public interface SimpleComponent {
        void inject(Example21 example);

        Map<String, Dependency1> dependency1s();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        @Singleton
        @IntoMap
        @StringKey("key 1")
        static Dependency1 provideDependency11() {
            return new Dependency11();
        }

        @Provides
        @IntoMap
        @StringKey("key 2")
        static Dependency1 provideDependency12() {
            return new Dependency12();
        }
    }

    static class Dependency1 {
    }

    static class Dependency11 extends Dependency1 {
    }

    static class Dependency12 extends Dependency1 {
    }

    @Singleton
    static class Dependency2 {
        @Inject
        Dependency2(Map<String, Dependency1> dependency1s) {
        }
    }
}
