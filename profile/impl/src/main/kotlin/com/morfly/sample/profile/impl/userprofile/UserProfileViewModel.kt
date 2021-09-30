package com.morfly.sample.profile.impl.userprofile

import androidx.lifecycle.ViewModel
import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.User
import com.morfly.sample.profile.impl.userprofile.di.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class UserProfileViewModel @Inject constructor(
    private val getUserImages: GetUserImages,
    private val getMyImages: GetMyImages,
    @UserId private val userId: String?,
) : ViewModel() {

    val isMyProfile = userId == null
    val showSettingsButton = userId == null
    val screenTitle = if (isMyProfile) "My Profile" else "Profile"

    private val mutableAvatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = mutableAvatarUrl

    private val mutableUsername = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = mutableUsername


    fun images(): Flow<List<Image>> {
        val imagesFlow =
            if (userId != null) getUserImages(userId)
            else getMyImages()

        return imagesFlow.onEach { images ->
            val user = images.firstOrNull()?.user
            if (user != null) updateUserInfo(user)
        }
    }

    fun updateUserInfo(user: User) {
        mutableAvatarUrl.value = user.imageUrl
        mutableUsername.value = user.name
    }
}

