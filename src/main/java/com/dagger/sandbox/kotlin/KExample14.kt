package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.*
import javax.inject.Singleton

/**
 * Reusable.
 * Bindings with this scope are not associated with any single component.
 * Each component that actually uses the binding will cache the returned or instantiated object.
 * Very useful when you want to limit the number of instances but don’t need to guarantee a single instance.
 */
class KExample14 : BaseExample() {

    override fun run() {
        val simpleComponent = DaggerKExample14_SimpleComponent.create()
        val simpleSubComponentA = simpleComponent.newSimpleSubComponentA()
        val simpleSubComponentB = simpleComponent.newSimpleSubComponentB()

        printSelf()
        for (i in 1..2) {
            val localDependency1A = simpleSubComponentA.dependency1()
            val localDependency2A = simpleSubComponentA.dependency2()
            val localDependency1B = simpleSubComponentB.dependency1()
            val localDependency2B = simpleSubComponentB.dependency2()
            printLocal(localDependency1A, "SubComponentA")
            printLocal(localDependency2A, "SubComponentA")
            printLocal(localDependency1B, "SubComponentB")
            printLocal(localDependency2B, "SubComponentB")
            printDivider()
        }
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun newSimpleSubComponentA(): SimpleSubComponentA

        fun newSimpleSubComponentB(): SimpleSubComponentB
    }

    @Module
    class SimpleModule {
        @Provides
        @Singleton
        fun provideDependency1() = Dependency1()

        @Provides
        @Reusable
        fun provideDependency(dep1: Dependency1) = Dependency2(dep1)
    }

    @Subcomponent
    interface SimpleSubComponentA {
        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Subcomponent
    interface SimpleSubComponentB {
        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    class Dependency1

    class Dependency2 constructor(dep1: Dependency1)
}