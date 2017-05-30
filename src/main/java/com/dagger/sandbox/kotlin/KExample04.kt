package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import javax.inject.Inject

/**
 * Dependencies instantiated by dagger have their fields with @Inject automatically injected.
 */
class KExample04 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample04_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
        printLocal(localDependency1.dependency3)
        printField(dependency1.dependency3)
    }

    @Component
    interface SimpleComponent {
        fun inject(ex4: KExample04)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    class Dependency1 @Inject constructor() {
        @Inject
        lateinit var dependency3: Dependency3
    }

    class Dependency2 @Inject constructor(dp1: Dependency1)

    class Dependency3 @Inject constructor()
}
