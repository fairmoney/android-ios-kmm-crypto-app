package dev.ohoussein.cryptoapp.data.database.crypto.mapper

import dev.ohoussein.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCryptoDetails
import javax.inject.Inject

class DbDomainModelMapper @Inject constructor() {

    fun convertDBCrypto(data: List<DBCrypto>): List<CryptoModel> = data.map { convert(it) }

    private fun convert(data: DBCrypto) = CryptoModel(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.imageUrl,
        price = data.price,
        priceChangePercentIn24h = data.priceChangePercentIn24h,
        order = data.order,
    )

    fun toDB(domain: List<CryptoModel>) = domain.map { toDB(it) }

    private fun toDB(domain: CryptoModel) = DBCrypto(
        id = domain.id,
        symbol = domain.symbol,
        name = domain.name,
        imageUrl = domain.imageUrl,
        price = domain.price,
        priceChangePercentIn24h = domain.priceChangePercentIn24h,
        order = domain.order,
    )

    fun toDomain(cryptoDetails: DBCryptoDetails): CryptoDetailsModel = CryptoDetailsModel(
        id = cryptoDetails.id,
        name = cryptoDetails.name,
        symbol = cryptoDetails.symbol,
        imageUrl = cryptoDetails.imageUrl,
        hashingAlgorithm = cryptoDetails.hashingAlgorithm,
        homePageUrl = cryptoDetails.homePageUrl,
        blockchainSite = cryptoDetails.blockchainSite,
        mainRepoUrl = cryptoDetails.mainRepoUrl,
        sentimentUpVotesPercentage = cryptoDetails.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = cryptoDetails.sentimentDownVotesPercentage,
        description = cryptoDetails.description,
    )

    fun toDB(cryptoDetails: CryptoDetailsModel): DBCryptoDetails = DBCryptoDetails(
        id = cryptoDetails.id,
        name = cryptoDetails.name,
        symbol = cryptoDetails.symbol,
        imageUrl = cryptoDetails.imageUrl,
        hashingAlgorithm = cryptoDetails.hashingAlgorithm,
        homePageUrl = cryptoDetails.homePageUrl,
        blockchainSite = cryptoDetails.blockchainSite,
        mainRepoUrl = cryptoDetails.mainRepoUrl,
        sentimentUpVotesPercentage = cryptoDetails.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = cryptoDetails.sentimentDownVotesPercentage,
        description = cryptoDetails.description,
    )
}
