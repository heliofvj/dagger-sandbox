package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 *  injections.
 * When you need many objects of the same type. Like a factory or pool.
 */
class KExample18 : BaseExample() {

    @Inject
    @Named("SomeKindOfDependency1")
    lateinit var someKindOfDependency1: Dependency1

    @Inject
    @Named("AnotherKindOfDependency1")
    lateinit var anotherKindOfDependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    override fun run() {
        val simpleComponent = DaggerKExample18_SimpleComponent.create()

        simpleComponent.inject(this)
        val localSomeKindOfDependency1 = simpleComponent.someKindOfDependency1()
        val localAnotherKindOfDependency1 = simpleComponent.anotherKindOfDependency1()
        val localDependency2 = simpleComponent.dependency2()

        printSelf()
        printLocal(someKindOfDependency1, "of some kind")
        printField(localSomeKindOfDependency1, "of some kind")
        printLocal(anotherKindOfDependency1, "of another kind")
        printField(localAnotherKindOfDependency1, "of another kind")
        printLocal(localDependency2)
        printField(dependency2)
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModule::class))
    interface SimpleComponent {
        fun inject(ex18: KExample18)

        @Named("SomeKindOfDependency1")
        fun someKindOfDependency1(): Dependency1

        @Named("AnotherKindOfDependency1")
        fun anotherKindOfDependency1(): Dependency1

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModule {
        @Provides
        @Named("SomeKindOfDependency1")
        fun someKindOfDependency1() = Dependency1()

        @Provides
        @Named("AnotherKindOfDependency1")
        fun anotherKindOfDependency1() = Dependency1()
    }

    class Dependency1 @Inject constructor()

    @Singleton
    class Dependency2 @Inject constructor(@Named("AnotherKindOfDependency1") dep1: Dependency1)
}