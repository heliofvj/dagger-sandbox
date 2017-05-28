package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

/**
 * Created by daividsilverio on 27/05/17.
 */
class KExample11 : BaseExample() {

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class MySubScope

    override fun run() {
        val simpleComponent = DaggerKExample11_SimpleComponent.create()
        val simpleSubComponentA = simpleComponent.newSimpleSubComponentA()
        val simpleSubComponentB = simpleComponent.newSimpleSubComponentB()

        printSelf()
        for(i in 1..2) {
            val localDependency1A = simpleSubComponentA.dependency1()
            val localDependency1B = simpleSubComponentB.dependency1()
            printLocal(localDependency1A)
            printLocal(localDependency1B)
        }
    }

    @Singleton
    @Component
    interface SimpleComponent {
        fun newSimpleSubComponentA(): SimpleSubComponentA

        fun newSimpleSubComponentB(): SimpleSubComponentB
    }

    @MySubScope
    @Subcomponent
    interface SimpleSubComponentA {
        fun dependency1(): Dependency1
    }

    @MySubScope
    @Subcomponent
    interface SimpleSubComponentB {
        fun dependency1(): Dependency1
    }


    @MySubScope
    class Dependency1 @Inject constructor()
}