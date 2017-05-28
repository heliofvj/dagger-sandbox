package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

/**
 * Sub-scopes and @Inject constructors.
 * You can annotate dependencies with sub-scopes too.
 * Of course, if you annotate a class with a sub-scope, the parent component will not be able to instantiate it.
 * A component can only reference bindings that have the same scope as the component or no scope at all.
 * (there is also @Reusable, but we'll talk about that later)
 */
class KExample10 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample10_SimpleComponent.create()
        val simpleSubComponent = simpleComponent.newSimpleSubComponent()

        simpleSubComponent.inject(this)
        val localDependency1 = simpleSubComponent.dependency1()
        val localDependency2 = simpleSubComponent.dependency2()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
    }


    @Singleton
    @Component
    interface SimpleComponent {
        fun newSimpleSubComponent(): SimpleSubComponent
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class MySubScope

    @MySubScope
    @Subcomponent
    interface SimpleSubComponent {
        fun inject(ex10: KExample10)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Singleton
    class Dependency1 @Inject constructor()

    @MySubScope
    class Dependency2 @Inject constructor(dp1: Dependency1)
}