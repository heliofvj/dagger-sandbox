package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton.
 * This annotation is a binding scope.
 * In a scoped component, the bindings (@Provides methods, @Inject constructors) annotated with the component scope
 * will only be called once.
 * The component will always return the same instance for scoped dependencies.
 */
class KExample07 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample07_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex7: KExample07)

        fun dependency1(): Dependency1

        fun dependency2(): Dependency2
    }


    @Module
    class SimpleModule {
        @Provides
        @Singleton
        fun dependency1() = Dependency1()

    }

    class Dependency1

    @Singleton
    class Dependency2 @Inject constructor(dp1: Dependency1)
}