package com.morfly.sample.profile.impl.userprofile.di

import com.morfly.sample.common.di.SubfeatureScoped
import com.morfly.sample.profile.impl.di.ProfileComponent
import com.morfly.sample.profile.impl.userprofile.UserProfileViewModel
import dagger.BindsInstance
import dagger.Component


@SubfeatureScoped
@Component(
    modules = [UserProfileModule::class],
    dependencies = [ProfileComponent::class]
)
interface UserProfileComponent {

    val viewModel: UserProfileViewModel


    @Component.Factory
    interface Factory {

        fun create(
            parent: ProfileComponent,
            @BindsInstance @UserId userId: String?
        ): UserProfileComponent
    }
}