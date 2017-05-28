package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sub-components.
 * These components inherit and extend the object graph of a parent component.
 * Annotate sub-components with the @Subcomponent annotation.
 * You add modules to it just like any component.
 * To build and attach the sub-component to a parent component, you declare it explicitly in the component interface.
 * Every time you use the interface you'll get a new component, so keep the instance after you call it.
 */
class KExample08 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample08_SimpleComponent.create()
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

    @Subcomponent(modules = arrayOf(SimpleModuleB::class))
    interface SimpleSubComponent {
        fun inject(ex8: KExample08)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModuleB {
        @Provides
        fun dependency2(dep1: Dependency1) = Dependency2(dep1)
    }

    class Dependency1

    class Dependency2 constructor(dp1: Dependency1)
}