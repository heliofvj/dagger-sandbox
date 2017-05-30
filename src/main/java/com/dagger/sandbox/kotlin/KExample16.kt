package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton
import dagger.Lazy

/**
 * Lazy injections.
 * When you know you need an object, but doesn't know exactly when.
 */
class KExample16 : BaseExample() {

    @Inject
    lateinit var dependency1Lazy: Lazy<Dependency1>

    @Inject
    lateinit var dependency2Lazy: Lazy<Dependency2>

    override fun run() {
        val simpleComponent = DaggerKExample16_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1Lazy = simpleComponent.dependency1Lazy()
        val localDependency2Lazy = simpleComponent.dependency2Lazy()

        printSelf()
        printLocal(localDependency1Lazy)
        printLocal(localDependency1Lazy.get())
        printLocal(localDependency1Lazy.get())
        printField(dependency1Lazy)
        printField(dependency1Lazy.get())
        printField(dependency1Lazy.get())
        printDivider()
        printLocal(localDependency2Lazy)
        printLocal(localDependency2Lazy.get())
        printLocal(localDependency2Lazy.get())
        printField(dependency2Lazy)
        printField(dependency2Lazy.get())
        printField(dependency2Lazy.get())
    }

    @Singleton
    @Component
    interface SimpleComponent {
        fun inject(ex16: KExample16)

        fun dependency1Lazy(): Lazy<Dependency1>

        fun dependency2Lazy(): Lazy<Dependency2>
    }

    class Dependency1 @Inject constructor()

    class Dependency2 @Inject constructor(dep1: Dependency1)
}