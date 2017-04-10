package com.dagger.sandbox;

import dagger.Component;
import dagger.Subcomponent;

import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Sub-scopes and @Inject constructors.
 * You can annotate dependencies with sub-scopes too.
 * Of course, if you annotate a class with a sub-scope, the parent component will not be able to instantiate it.
 * A component can only reference bindings that have the same scope as the component or no scope at all.
 * (there is also @Reusable, but we'll talk about that later)
 */
class Example10 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample10_SimpleComponent.create();
        SimpleSubComponent simpleSubComponent = simpleComponent.newSimpleSubComponent();

        simpleSubComponent.inject(this);
        Dependency1 localDependency1 = simpleSubComponent.dependency1();
        Dependency2 localDependency2 = simpleSubComponent.dependency2();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
    }

    @Singleton
    @Component
    public interface SimpleComponent {
        SimpleSubComponent newSimpleSubComponent();
    }

    @Documented
    @Retention(RUNTIME)
    @Scope
    @interface MySubScope {
    }

    @MySubScope
    @Subcomponent
    public interface SimpleSubComponent {
        void inject(Example10 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Singleton
    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }

    @MySubScope
    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }
}
