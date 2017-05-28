package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

/**
 * You can install sub-components in two other ways:
 * <p>
 * In the component interface: declaring a sub-component builder instead of the actual sub-component.
 * Useful when your sub-component builder requires something that the parent component can't inject.
 * <p>
 * In a module: you can declare the sub-component classes in a module attached to the parent.
 * This removes the sub-component declarations from the component interface.
 * Unfortunately, it also means you cant directly get a instance of the sub-component through the component interface.
 * Instead, you need to inject the sub-component builder in a class field.
 * When you use Module.subcomponents, all those sub-components need to declare a @Subcomponent.Builder interface.
 * And you can't inject the sub-components themselves, only their builders.
 * This is the preferred way of installing sub-components.
 */
class KExample12 : BaseExample() {

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class MySubScope

    @Inject
    lateinit var simpleSubComponentABuilder: SimpleSubComponentA.Builder

    override fun run() {
        val simpleComponent = DaggerKExample12_SimpleComponent.create()
        simpleComponent.inject(this)
        val simpleSubComponentA = simpleSubComponentABuilder.build()
        val simpleSubComponentB = simpleComponent.simpleSubComponentBBuilder()
                .somethingTheParentCantInject("Yo")
                .build()

        printSelf()
        for (i in 1..2) {
            val localDependency1A = simpleSubComponentA.dependency1()
            val localDependency1B = simpleSubComponentB.dependency1()
            printLocal(localDependency1A)
            printLocal(localDependency1B)
        }
    }

    @Singleton
    @Component(modules = arrayOf(SubComponentsModule::class))
    interface SimpleComponent {
        fun inject(ex12: KExample12)

        fun simpleSubComponentBBuilder(): SimpleSubComponentB.Builder
    }

    @Module(subcomponents = arrayOf(SimpleSubComponentA::class))
    class SubComponentsModule


    @MySubScope
    @Subcomponent
    interface SimpleSubComponentA {
        fun dependency1(): Dependency1

        @Subcomponent.Builder
        interface Builder {
            fun build(): SimpleSubComponentA
        }

    }

    @MySubScope
    @Subcomponent
    interface SimpleSubComponentB {
        fun dependency1(): Dependency1

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            fun somethingTheParentCantInject(any: Any): Builder

            fun build(): SimpleSubComponentB
        }
    }


    @MySubScope
    class Dependency1 @Inject constructor()
}