package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import com.dagger.sandbox.Dependency1Interface
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject

/**
 * You can add @BindsInstance methods to component builders to allow instances to be directly injected in the components.
 * This should be preferred to writing a @Module with constructor arguments and immediately providing those values.
 */
class KExample06 : BaseExample(), Dependency1Interface {

    @Inject
    lateinit var dependency1Interface: Dependency1Interface

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample06_SimpleComponent.builder()
                .dependency1(this)
                .build()

        simpleComponent.inject(this)

        val localDependency1Interface = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1Interface)
        printField(dependency1Interface)
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Component
    interface SimpleComponent {
        fun inject(ex6: KExample06)

        fun dependency1(): Dependency1Interface

        fun dependency2(): Dependency2

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun dependency1(dependency1Interface: Dependency1Interface): Builder

            fun build(): SimpleComponent
        }
    }

    class Dependency2 @Inject constructor(dp1: Dependency1Interface)
}