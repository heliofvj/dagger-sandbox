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
 * Sub-scopes.
 * Of course you can use scoped bindings in sub-components!
 * Root components generally use the @Singleton scope.
 * For sub-components you need to create new scope annotations.
 */
class KExample09 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample09_SimpleComponent.create()
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
    @Component(modules = arrayOf(SimpleModuleA::class))
    interface SimpleComponent {
        fun newSimpleSubComponent(): SimpleSubComponent
    }

    @Module
    class SimpleModuleA {
        @Provides
        @Singleton
        fun dependency1() = Dependency1()
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class MySubScope

    @MySubScope
    @Subcomponent(modules = arrayOf(SimpleModuleB::class))
    interface SimpleSubComponent {
        fun inject(ex9: KExample09)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModuleB {

        @MySubScope
        @Provides
        fun dependency2(dep1: Dependency1) = Dependency2(dep1)
    }

    class Dependency1

    class Dependency2 constructor(dp1: Dependency1)
}