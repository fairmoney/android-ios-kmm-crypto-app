package dev.ohoussein.cryptoapp.data.network

import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataDebugModule = module {
    single<Array<Interceptor>>(named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR)) { emptyArray() }
    single<Array<Interceptor>>(named(DIConstants.Qualifier.HTTP_INTERCEPTOR)) { emptyArray() }
}
