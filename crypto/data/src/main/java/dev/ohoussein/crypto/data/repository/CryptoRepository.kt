package dev.ohoussein.crypto.data.repository

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.domain.model.CryptoModel
import dev.ohoussein.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.core.Qualifier
import dev.ohoussein.cryptoapp.data.cache.CachedDataRepository
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class CryptoRepository @Inject constructor(
    private val service: ApiCryptoService,
    private val cryptoDao: CryptoDAO,
    private val apiMapper: ApiDomainModelMapper,
    private val dbMapper: DbDomainModelMapper,
    @Named(Qualifier.CURRENCY) private val currency: String,
) : ICryptoRepository {

    private val topCryptoListCache: CachedDataRepository<Unit, List<CryptoModel>> = CachedDataRepository(
        updater = {
            val apiResponse = service.getTopCrypto(currency)
            apiMapper.convert(apiResponse)
        },
        cacheStreamer = {
            cryptoDao.getAll()
                .map(dbMapper::convertDBCrypto)
                .filterNot { it.isEmpty() }
        },
        cacheWriter = { _, data ->
            cryptoDao.replace(dbMapper.toDB(data))
        },
    )

    private val cryptoDetailsCache: CachedDataRepository<String, CryptoDetailsModel> = CachedDataRepository(
        updater = { id ->
            val response = service.getCryptoDetails(id)
            apiMapper.convert(response)
        },
        cacheStreamer = { id ->
            cryptoDao.getCryptoDetails(id)
                .filterNotNull()
                .map(dbMapper::toDomain)
        },
        cacheWriter = { _, data ->
            cryptoDao.insert(dbMapper.toDB(data))
        },
    )

    override fun getTopCryptoList(): Flow<List<CryptoModel>> = topCryptoListCache.stream(Unit)

    override suspend fun refreshTopCryptoList() = topCryptoListCache.refresh(Unit)

    override suspend fun refreshCryptoDetails(cryptoId: String) = cryptoDetailsCache.refresh(cryptoId)

    override fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel> = cryptoDetailsCache.stream(cryptoId)
}
