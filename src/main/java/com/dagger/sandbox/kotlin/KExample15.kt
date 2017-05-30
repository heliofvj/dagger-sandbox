package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.releasablereferences.CanReleaseReferences
import dagger.releasablereferences.ForReleasableReferences
import dagger.releasablereferences.ReleasableReferenceManager
import javax.inject.Inject
import javax.inject.Scope

/**
 * Releasable references.
 * Create a scope and annotate it with @CanReleaseReferences.
 * Then you can inject a ReleasableReferenceManager object and call releaseStrongReferences() on it.
 * That will make the component hold a WeakReference to the object instead of a strong reference.
 * Restore the strong references by calling restoreStrongReferences().
 */
class KExample15 : BaseExample() {

    @Inject
    @ForReleasableReferences(MyReleasableScope::class)
    lateinit var releasableReferenceManager: ReleasableReferenceManager

    override fun run() {
        val simpleComponent = DaggerKExample15_SimpleComponent.create()
        printSelf()

        injectAndPrint(simpleComponent)
        printDivider()

        releasableReferenceManager.releaseStrongReferences()
        System.gc()
        injectAndPrint(simpleComponent)
        printDivider()

        releasableReferenceManager.restoreStrongReferences()
        injectAndPrint(simpleComponent)
    }

    fun injectAndPrint(simpleComponent: SimpleComponent) {
        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printLocal(localDependency1)
        printLocal(localDependency2)
    }

    @MyReleasableScope
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex15: KExample15)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModule {
        @Provides
        @MyReleasableScope
        fun dependency1() = Dependency1()
    }

    class Dependency1

    @MyReleasableScope
    class Dependency2 @Inject constructor(dep1: Dependency1)


    @Retention(AnnotationRetention.RUNTIME)
    @CanReleaseReferences
    @Scope
    annotation class MyReleasableScope
}