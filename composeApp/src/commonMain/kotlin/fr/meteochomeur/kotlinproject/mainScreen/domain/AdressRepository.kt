package fr.meteochomeur.kotlinproject.mainScreen.domain

import fr.meteochomeur.kotlinproject.mainScreen.domain.util.DataError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.Result

interface AddressRepository {
    suspend fun getAddressesByQuery(query: String): Result<List<Address>, DataError.Remote>
    suspend fun getAddressesByCoordinates(
        x: Double,
        y: Double
    ): Result<List<Address>, DataError.Remote>
}