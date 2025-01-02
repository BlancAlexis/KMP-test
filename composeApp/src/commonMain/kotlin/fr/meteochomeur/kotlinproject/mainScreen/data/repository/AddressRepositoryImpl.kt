package fr.meteochomeur.kotlinproject.mainScreen.data.repository

import fr.meteochomeur.kotlinproject.mainScreen.data.AddressDataSource
import fr.meteochomeur.kotlinproject.mainScreen.domain.Address
import fr.meteochomeur.kotlinproject.mainScreen.domain.AddressRepository
import fr.meteochomeur.kotlinproject.mainScreen.domain.GeoCord
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.DataError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.Result
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.map


class AddressRepositoryImpl(private val addressDataSource: AddressDataSource) : AddressRepository {
    override suspend fun getAddressesByQuery(query: String): Result<List<Address>, DataError.Remote> {
        return addressDataSource.getAddressesByQuery(query).map { apiResponse ->
            apiResponse.features.map { feature ->
                Address(
                    rue = feature.properties.name,
                    infoComp = feature.properties.context,
                    geoCord = GeoCord(
                        feature.geometry.coordinates[0],
                        feature.geometry.coordinates[1]
                    )
                )
            }
        }
    }

    override suspend fun getAddressesByCoordinates(
        x: Double,
        y: Double
    ): Result<List<Address>, DataError.Remote> {
        return addressDataSource.getAddressesByCoordinates(x, y).map { apiResponse ->
            apiResponse.features.map { feature ->
                Address(
                    rue = feature.properties.name,
                    infoComp = feature.properties.context,
                    geoCord = GeoCord(
                        feature.geometry.coordinates[0],
                        feature.geometry.coordinates[1]
                    )
                )
            }
        }
    }
}