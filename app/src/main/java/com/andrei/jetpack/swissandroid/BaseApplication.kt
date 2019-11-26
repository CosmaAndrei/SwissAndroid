package com.andrei.jetpack.swissandroid

import com.andrei.jetpack.swissandroid.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()
}
