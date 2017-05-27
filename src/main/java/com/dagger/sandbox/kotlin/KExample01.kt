package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by daividsilverio on 27/05/17.
 */
class KExample01 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample01_SimpleComponent.create()

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
        fun inject(ex1: KExample01)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }


    @Module
    class SimpleModule {
        @Provides
        fun dependency1() = Dependency1()

        @Provides
        fun dependency2(dep1: Dependency1) = Dependency2(dep1)
    }

    class Dependency1

    class Dependency2 constructor(dp1: Dependency1)
}