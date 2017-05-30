package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * IntoMap.
 * Dagger allows you to add elements to an injectable map as long as the map keys are known at compile time.
 * Create a binding that is annotated with @IntoMap and another annotation that specifies the map key for that entry.
 */
class KExample21 : BaseExample() {

    @Inject
    lateinit var dependencies: Map<String, @JvmSuppressWildcards Dependency1>

    override fun run() {
        val simpleComponent = DaggerKExample21_SimpleComponent.create()

        simpleComponent.inject(this)
        val localDependencies = simpleComponent.dependency1s()

        printSelf()
        dependencies.forEach { key, value -> printField(value, key) }
        printDivider()
        localDependencies.forEach { key, value -> printLocal(value, key) }
    }

    @Singleton
    @Component(modules = arrayOf(SimpleModuleA::class))
    interface SimpleComponent {
        fun inject(kex21: KExample21)

        fun dependency1s(): Map<String, Dependency1>

        fun dependency2(): Dependency2
    }

    @Module
    class SimpleModuleA {

        @Singleton
        @Provides
        @IntoMap
        @StringKey("key 1")
        fun provideDependency11(): Dependency1 = Dependency11()

        @Provides
        @IntoMap
        @StringKey("key 2")
        fun provideDependency12(): Dependency1 = Dependency12()
    }

    open class Dependency1
    class Dependency11 : Dependency1()
    class Dependency12 : Dependency1()

    @Singleton
    class Dependency2 @Inject constructor(dp1s: Map<String, @JvmSuppressWildcards Dependency1>)
}