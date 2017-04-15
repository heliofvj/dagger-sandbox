package com.dagger.sandbox;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.releasablereferences.CanReleaseReferences;
import dagger.releasablereferences.ForReleasableReferences;
import dagger.releasablereferences.ReleasableReferenceManager;

import javax.inject.Inject;
import javax.inject.Scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Releasable references.
 * Create a scope and annotate it with @CanReleaseReferences.
 * Then you can inject a ReleasableReferenceManager object and call releaseStrongReferences() on it.
 * That will make the component hold a WeakReference to the object instead of a strong reference.
 * Restore the strong references by calling restoreStrongReferences().
 */
class Example15 extends BaseExample {

    @Inject
    @ForReleasableReferences(MyReleasableScope.class)
    ReleasableReferenceManager releasableReferenceManager;

    @Override
    public void run() {
        SimpleComponent simpleComponent = DaggerExample15_SimpleComponent.create();
        printSelf();

        injectAndPrint(simpleComponent);
        printDivider();

        releasableReferenceManager.releaseStrongReferences();
        System.gc();
        injectAndPrint(simpleComponent);
        printDivider();

        releasableReferenceManager.restoreStrongReferences();
        injectAndPrint(simpleComponent);
    }

    private void injectAndPrint(SimpleComponent simpleComponent) {
        simpleComponent.inject(this);
        Dependency1 localDependency1 = simpleComponent.dependency1();
        Dependency2 localDependency2 = simpleComponent.dependency2();

        printLocal(localDependency1);
        printLocal(localDependency2);
    }

    @MyReleasableScope
    @Component(modules = SimpleModule.class)
    public interface SimpleComponent {
        void inject(Example15 example);

        Dependency1 dependency1();

        Dependency2 dependency2();
    }

    @Module
    static class SimpleModule {
        @Provides
        @MyReleasableScope
        static Dependency1 provideDependency1() {
            return new Dependency1();
        }
    }

    static class Dependency1 {
    }

    @MyReleasableScope
    static class Dependency2 {
        @Inject
        Dependency2(Dependency1 dependency1) {
        }
    }

    @Documented
    @Retention(RUNTIME)
    @CanReleaseReferences
    @Scope
    @interface MyReleasableScope {
    }
}
