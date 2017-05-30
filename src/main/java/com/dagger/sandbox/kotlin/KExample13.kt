package com.dagger.sandbox.kotlin

import com.dagger.sandbox.BaseExample
import dagger.Component
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

/**
 * Created by daividsilverio on 28/05/17.
 */
class KExample13 : BaseExample() {

    @Inject
    lateinit var dependency1: Dependency1

    @Inject
    lateinit var dependency2: Dependency2

    @Inject
    lateinit var dependency3: Dependency3

    override fun run() {
        val simpleComponent = DaggerKExample13_SimpleComponent.create()
        val simpleSubComponentB = simpleComponent.newSimpleSubComponentA().newSimpleSubComponentB()

        simpleSubComponentB.inject(this)

        val localDependency1 = simpleSubComponentB.dependency1()
        val localDependency2 = simpleSubComponentB.dependency2()
        val localDependency3 = simpleSubComponentB.dependency3()

        printSelf()
        printLocal(localDependency1)
        printField(dependency1)
        printLocal(localDependency2)
        printField(dependency2)
        printLocal(localDependency3)
        printField(dependency3)
    }

    @Singleton
    @Component
    interface SimpleComponent {
        fun newSimpleSubComponentA(): SimpleSubComponentA
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class SmallerScope

    @SmallerScope
    @Subcomponent
    interface SimpleSubComponentA {
        fun newSimpleSubComponentB(): SimpleSubComponentB
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Scope
    annotation class EvenSmallerScope


    @EvenSmallerScope
    @Subcomponent
    interface SimpleSubComponentB {
        fun inject(ex13: KExample13)

        fun dependency1(): Dependency1
        fun dependency2(): Dependency2
        fun dependency3(): Dependency3
    }

    @Singleton
    class Dependency1 @Inject constructor()

    @SmallerScope
    class Dependency2 @Inject constructor(dep1: Dependency1)

    @EvenSmallerScope
    class Dependency3 @Inject constructor(dep1: Dependency1, dep2: Dependency2)
}