package dev.ohoussein.crypto.domain.repo

import dev.ohoussein.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.crypto.domain.model.CryptoModel
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(): Flow<List<CryptoModel>>

    suspend fun refreshTopCryptoList()

    fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel>

    suspend fun refreshCryptoDetails(cryptoId: String)
}
