package io.morfly.streaming.common.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController


@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: @DisallowComposableCalls () -> VM
): VM {
    val factory = remember(key) {
        object : ViewModelProvider.Factory {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                @Suppress("UNCHECKED_CAST")
                return viewModelInstanceCreator() as VM
            }
        }
    }
    return viewModel(key = key, factory = factory)
}

@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String? = null,
    crossinline viewModelInstanceCreator: @DisallowComposableCalls () -> VM
): VM {
    val factory = remember(key) {
        object : ViewModelProvider.Factory {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                @Suppress("UNCHECKED_CAST")
                return viewModelInstanceCreator() as VM
            }
        }
    }
    return viewModel(viewModelStoreOwner, key, factory)
}

@Composable
fun NavBackStackEntry.rememberBackStackEntry(
    navController: NavHostController,
    route: String
): NavBackStackEntry {
    return remember(this) { navController.getBackStackEntry(route) }
}


fun <T> HasDefaultViewModelProviderFactory.remember(
    key: CreationExtras.Key<T>,
    initializer: () -> T
): T {
    val extras = defaultViewModelCreationExtras as? MutableCreationExtras ?: return initializer()
    if (extras[key] == null) {
        extras[key] = initializer()
    }
    return extras[key]!!
}