package com.akki.meesholibraryassignment.viewmodels

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@kotlin.annotation.Retention
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)