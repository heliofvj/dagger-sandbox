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
 * Multiple Sub-components.
 * Funny thing about sub-components: they can all use the same scope.
 * That doesn't mean that sub-component A and B share the same graph, no.
 * A and B are siblings, but don't share any data except the one provided by their parent.
 * Weird? Yeah, a bit.
 * Just remember that scope annotations are just that: annotations.
 * The objects that are actually storing data, and therefore are the real scopes, are the components.
 */
class Example11 extends BaseExample {

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample11_SimpleComponent.create();
        SimpleSubComponentA simpleSubComponentA = simpleComponent.newSimpleSubComponentA();
        SimpleSubComponentB simpleSubComponentB = simpleComponent.newSimpleSubComponentB();

        printSelf();
        for (int i = 0; i < 2; i++) {
            Dependency1 localDependency1A = simpleSubComponentA.dependency1();
            Dependency1 localDependency1B = simpleSubComponentB.dependency1();
            printLocal(localDependency1A);
            printLocal(localDependency1B);
        }
    }

    @Singleton
    @Component
    public interface SimpleComponent {
        SimpleSubComponentA newSimpleSubComponentA();

        SimpleSubComponentB newSimpleSubComponentB();
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
    }

    @MySubScope
    @Subcomponent
    public interface SimpleSubComponentB {
        Dependency1 dependency1();
    }

    @MySubScope
    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }
}
