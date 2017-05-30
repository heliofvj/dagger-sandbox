package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Binds.
 * Can be used to provide implementations of interfaces and abstract classes or provide subclasses as superclasses.
 * Requires dependencies that can be injected automatically.
 * Easier than a provider, since you don't need to instantiate the dependency, nor declare and update method params.
 */
class KExample22 : BaseExample() {

    @Inject
    lateinit var dependency1Abstract: Dependency1Abstract

    @Inject
    lateinit var dependency2Interface: Dependency2Interface

    override fun run() {
        val simpleComponent = DaggerKExample22_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1Abstract = simpleComponent.dependency1Abstract()
        val localDependency2Interface = simpleComponent.dependency2Interface()

        printSelf()
        printLocal(localDependency1Abstract)
        printField(dependency1Abstract)
        printLocal(localDependency2Interface)
        printField(dependency2Interface)
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(kex22: KExample22)

        fun dependency1Abstract(): Dependency1Abstract

        fun dependency2Interface(): Dependency2Interface
    }

    @Module
    abstract class SimpleModule {
        @Binds
        abstract fun bindDependency1(dep1: Dependency1): Dependency1Abstract

        @Binds
        @Singleton
        abstract fun bindDependency2(dep2: Dependency2): Dependency2Interface
    }

    abstract class Dependency1Abstract

    interface Dependency2Interface

    @Singleton
    class Dependency1 @Inject constructor() : Dependency1Abstract()

    class Dependency2 @Inject constructor(abstractDependency1: Dependency1Abstract) : Dependency2Interface
}