package com.morfly.sample.profile.impl.settings.di

import com.morfly.sample.common.di.SubfeatureScoped
import com.morfly.sample.profile.impl.di.ProfileComponent
import com.morfly.sample.profile.impl.settings.SettingsViewModel
import dagger.Component


@SubfeatureScoped
@Component(
    modules = [SettingsModule::class],
    dependencies = [ProfileComponent::class]
)
interface SettingsComponent {

    val viewModel: SettingsViewModel
}