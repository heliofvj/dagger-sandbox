package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import javax.inject.Inject

/**
 * Created by daividsilverio on 27/05/17.
 */
class KExample03 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample03_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Component
    interface SimpleComponent {
        fun inject(ex3: KExample03)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    class Dependency1 @Inject constructor()

    class Dependency2 @Inject constructor(dp1: Dependency1)
}