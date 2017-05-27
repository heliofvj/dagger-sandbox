package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Dependencies can be automatically constructed made available using @Inject on constructors.
 */
class KExample02 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample02_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex2: KExample02)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }


    @Module
    class SimpleModule {
        @Provides
        fun dependency1() = Dependency1()

    }

    class Dependency1

    class Dependency2 @Inject constructor(dp1: Dependency1)
}