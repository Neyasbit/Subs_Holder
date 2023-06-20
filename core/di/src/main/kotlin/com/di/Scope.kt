package com.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope

@OptIn(KoinInternalApi::class)
internal val LocalScope = compositionLocalOf {
    getGlobalKoin.scopeRegistry.rootScope
}

@Composable
fun KoinScope(
    getScope: Koin.() -> Scope,
    content: @Composable () -> Unit,
) {
    val currentScope = LocalScope.current
    val koin = getKoin()
    val scope = remember {
        koin.getScope().also {
            it.linkTo(currentScope)
        }
    }
    CompositionLocalProvider(LocalScope provides scope) {
        content()
    }
}

@Composable
fun getScope() = LocalScope.current
