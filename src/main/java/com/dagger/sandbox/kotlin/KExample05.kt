package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import com.dagger.sandbox.Dependency1Interface
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Often you only have data available at the time you’re building a component.
 * You can give this data to a module through its constructor.
 * Then the module can provide the data to the component.
 * All modules with a non default constructor needs to be instantiated manually.
 */
class KExample05 : BaseExample(), Dependency1Interface {

    @Inject
    lateinit var dependency1Interface: Dependency1Interface

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample05_SimpleComponent.builder()
                .simpleModule(SimpleModule(this))
                .build()

        simpleComponent.inject(this)

        val localDependency1Interface = simpleComponent.dependency1Interface()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1Interface)
        printField(dependency1Interface)
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex5: KExample05)

        fun dependency1Interface(): Dependency1Interface

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModule @Inject constructor(private val dependency1Interface: Dependency1Interface) {

        @Provides
        fun providesDependency1Interface() = dependency1Interface
    }

    class Dependency2 @Inject constructor(dependency1Interface: Dependency1Interface)
}