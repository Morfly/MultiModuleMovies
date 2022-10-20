package io.morfly.streaming.common.di

import dagger.MapKey
import io.morfly.streaming.common.FeatureEntry
import kotlin.reflect.KClass


@MapKey
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)