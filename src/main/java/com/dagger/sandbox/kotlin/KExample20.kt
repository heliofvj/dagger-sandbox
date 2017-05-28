package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

/**
 * IntoSet and ElementsIntoSet.
 * Dagger allows you to bind several objects into a collection even when the objects are bound in different modules
 * To add an element to an injectable multibound set, add an IntoSet/ElementsIntoSet annotation to your module method.
 */
class KExample20 : BaseExample() {

    @Inject
    lateinit var dependencies: Set<@JvmSuppressWildcards Dependency1>
    //or lateinit var dependencies: MutableSet<Dependency1>

    override fun run() {
        val simpleComponent = DaggerKExample20_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependencies = simpleComponent.dependency1s()

        printSelf()
        dependencies.forEach { printField(it) }
        printDivider()
        localDependencies.forEach { printLocal(it) }
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModuleA::class, SimpleModuleB::class))
    interface SimpleComponent {
        fun inject(kex20: KExample20)

        fun dependency1s(): Set<Dependency1>

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModuleA {
        /**
         * Well, I couldn't make it work with kotlin for now
         * Anyway, You can always generate your graph using java as well
         */

        @Provides
        @Singleton
        @IntoSet
        fun provideDependency11() = Dependency11()

        @Provides
        @IntoSet
        fun provideDependency12() = Dependency12()
    }

    @Module
    class SimpleModuleB {
        /**
         * Those work just fine. Run the project to see the difference from the java version
         */
        @Provides
        @Singleton
        @ElementsIntoSet
        fun provideDependencies13(): Set<Dependency1> = (1..2).map { Dependency13() }.toSet()

        @Provides
        @ElementsIntoSet
        fun provideDependencies14(): Set<Dependency1> = (1..2).map { Dependency14() }.toSet()
    }

    open class Dependency1
    class Dependency11 : Dependency1()
    class Dependency12 : Dependency1()
    class Dependency13 : Dependency1()
    class Dependency14 : Dependency1()

    @Singleton
    class Dependency2 @Inject constructor(dep1s: Set<@JvmSuppressWildcards Dependency1>)
}