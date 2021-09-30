package com.morfly.sample.common.di

import com.morfly.sample.common.FeatureEntry
import dagger.MapKey
import kotlin.reflect.KClass


@MapKey
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)