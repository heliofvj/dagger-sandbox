package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Bonus!
 * Here we use some of the cool features we've seen to build a sub-component map provider.
 */
class KExample23 : BaseExample() {

    @Inject
    lateinit var componentBuilderProviderMap: Map<Class<*>, @JvmSuppressWildcards Provider<ComponentBuilder<*>>>

    override fun run() {
        val simpleComponent = DaggerKExample23_SimpleComponent.create()
        simpleComponent.inject(this)

        val simpleSubComponentA: SimpleSubComponentA = getComponentBuilder(SimpleSubComponentA::class.java).build()
        val simpleSubComponentB: SimpleSubComponentB = getComponentBuilder(SimpleSubComponentB::class.java).build()

        printSelf()
        for (i in 1..2) {
            val localDependency1A = simpleSubComponentA.dependency1()
            val localDependency1B = simpleSubComponentB.dependency1()
            printLocal(localDependency1A)
            printLocal(localDependency1B)
        }
    }

    interface ComponentBuilder<Comp> {
        fun build(): Comp
    }

    private fun <Comp : Any> getComponentBuilder(componentClass: Class<Comp>): ComponentBuilder<Comp> {
        return (componentBuilderProviderMap.get(componentClass) as Provider).get() as ComponentBuilder<Comp>
    }

    @Singleton
    @Component(modules = arrayOf(SubComponentsModule::class))
    interface SimpleComponent {
        fun inject(kex23: KExample23)
    }

    @Module(subcomponents = arrayOf(SimpleSubComponentA::class, SimpleSubComponentB::class))
    abstract class SubComponentsModule {
        @Binds
        @IntoMap
        @ClassKey(SimpleSubComponentA::class)
        abstract fun bindSimpleSubComponentABuilder(builder: SimpleSubComponentA.Builder): ComponentBuilder<*>

        @Binds
        @IntoMap
        @ClassKey(SimpleSubComponentB::class)
        abstract fun bindSimpleSubComponentBBuilder(builder: SimpleSubComponentB.Builder): ComponentBuilder<*>
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class MySubScope


    @MySubScope
    @Subcomponent
    interface SimpleSubComponentA {
        fun dependency1(): Dependency1

        @Subcomponent.Builder
        interface Builder : ComponentBuilder<SimpleSubComponentA>
    }

    @MySubScope
    @Subcomponent
    interface SimpleSubComponentB {
        fun dependency1(): Dependency1

        @Subcomponent.Builder
        interface Builder : ComponentBuilder<SimpleSubComponentB>
    }

    @MySubScope
    class Dependency1 @Inject constructor()
}
