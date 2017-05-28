package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Provider injections.
 * When you need many objects of the same type. Like a factory or pool.
 */
class KExample17 : BaseExample() {

    @Inject
    lateinit var dependency1Provider: Provider<Dependency1>

    @Inject
    lateinit var dependency2Provider: Provider<Dependency2>

    override fun run() {
        val simpleComponent = DaggerKExample17_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1Provider = simpleComponent.dependency1Provider()
        val localDependency2Provider = simpleComponent.dependency2Provider()

        printSelf()
        printLocal(localDependency1Provider)
        printLocal(localDependency1Provider.get())
        printLocal(localDependency1Provider.get())
        printField(dependency1Provider)
        printField(dependency1Provider.get())
        printField(dependency1Provider.get())
        printDivider()
        printLocal(localDependency2Provider)
        printLocal(localDependency2Provider.get())
        printLocal(localDependency2Provider.get())
        printField(dependency2Provider)
        printField(dependency2Provider.get())
        printField(dependency2Provider.get())
    }

    @Singleton
    @Component
    interface SimpleComponent {
        fun inject(ex17: KExample17)

        fun dependency1Provider(): Provider<Dependency1>

        fun dependency2Provider(): Provider<Dependency2>
    }

    class Dependency1 @Inject constructor()

    class Dependency2 @Inject constructor(dep1: Dependency1)
}