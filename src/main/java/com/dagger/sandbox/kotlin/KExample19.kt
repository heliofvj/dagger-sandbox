package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.*
import java.util.*
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

/**
 * Optional bindings.
 * If you want a binding to work even if some dependency is not bound in the component.
 * When would you use this? I have no idea!
 */
class KExample19 : BaseExample() {

    @Inject
    lateinit var dependency1: Optional<Dependency1>

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample19_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependency1 = simpleComponent.dependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(localDependency1,  if (localDependency1.isPresent) getHashMessage(localDependency1.get()) else "isNull")
        printField(dependency1, if (dependency1.isPresent) getHashMessage(dependency1.get()) else "isNull")
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex19: KExample19)

        fun dependency1(): Optional<Dependency1>

        fun dependency2(): Dependency2
    }


    @Module
    abstract class SimpleModule {
        @Singleton
        @BindsOptionalOf
        abstract fun dependency1(): Dependency1

    }

    class Dependency1

    @Singleton
    class Dependency2 @Inject constructor(dp1: Optional<Dependency1>)
}