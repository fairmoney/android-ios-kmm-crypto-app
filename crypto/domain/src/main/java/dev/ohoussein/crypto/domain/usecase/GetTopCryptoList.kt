package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.CryptoModel
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    operator fun invoke(): Flow<List<CryptoModel>> {
        return cryptoRepository.getTopCryptoList()
    }

    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
