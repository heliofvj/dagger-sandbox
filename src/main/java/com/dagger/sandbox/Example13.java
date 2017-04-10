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
 * Oh, and sub-components can have sub-components too!
 */
class Example13 extends BaseExample {

    @Inject
    Dependency1 dependency1;
    @Inject
    Dependency2 dependency2;
    @Inject
    Dependency3 dependency3;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample13_SimpleComponent.create();
        SimpleSubComponentB simpleSubComponentB = simpleComponent.newSimpleSubComponentA().newSimpleSubComponentB();

        simpleSubComponentB.inject(this);
        Dependency1 localDependency1 = simpleSubComponentB.dependency1();
        Dependency2 localDependency2 = simpleSubComponentB.dependency2();
        Dependency3 localDependency3 = simpleSubComponentB.dependency3();

        printSelf();
        printLocal(localDependency1);
        printField(dependency1);
        printLocal(localDependency2);
        printField(dependency2);
        printLocal(localDependency3);
        printField(dependency3);
    }

    @Singleton
    @Component
    public interface SimpleComponent {
        SimpleSubComponentA newSimpleSubComponentA();
    }

    @Documented
    @Retention(RUNTIME)
    @Scope
    @interface SmallerScope {
    }

    @SmallerScope
    @Subcomponent
    public interface SimpleSubComponentA {
        SimpleSubComponentB newSimpleSubComponentB();
    }

    @Documented
    @Retention(RUNTIME)
    @Scope
    @interface EvenSmallerScope {
    }

    @EvenSmallerScope
    @Subcomponent
    public interface SimpleSubComponentB {
        void inject(Example13 example);

        Dependency1 dependency1();

        Dependency2 dependency2();

        Dependency3 dependency3();
    }

    @Singleton
    static class Dependency1 {
        @Inject
        Dependency1() {
        }
    }

    @SmallerScope
    static class Dependency2 {
        @Inject
        Dependency2(Example10.Dependency1 dependency1) {
        }
    }

    @EvenSmallerScope
    static class Dependency3 {
        @Inject
        Dependency3(Dependency1 dependency1, Dependency2 dependency2) {
        }
    }
}
