package com.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication

internal val getGlobalKoin: Koin = GlobalContext.get()

@PublishedApi
internal val LocalKoin = compositionLocalOf { getGlobalKoin }

@OptIn(KoinInternalApi::class)
@Composable
fun Koin(
    koinApplication: KoinApplication,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalKoin provides koinApplication.koin,
        LocalScope provides koinApplication.koin.scopeRegistry.rootScope,
    ) {
        content()
    }
}

@Composable
fun Koin(
    appDeclaration: KoinAppDeclaration? = null,
    content: @Composable () -> Unit,
) {
    Koin(koinApplication(appDeclaration)) {
        content()
    }
}

@Composable
fun getKoin(): Koin = LocalKoin.current
