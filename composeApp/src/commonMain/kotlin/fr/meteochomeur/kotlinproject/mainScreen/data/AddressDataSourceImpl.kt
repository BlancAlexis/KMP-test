package fr.meteochomeur.kotlinproject.mainScreen.data


import fr.meteochomeur.kotlinproject.mainScreen.data.dto.ApiResponseDto
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.DataError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


private const val BASE_URL = "https://api-adresse.data.gouv.fr"
private const val LIMIT = 5

class AddressDataSourceImpl(private val httpClient: HttpClient) : AddressDataSource {
    override suspend fun getAddressesByQuery(query: String): Result<ApiResponseDto, DataError.Remote> {
        return safeCall<ApiResponseDto> {
            httpClient.get(
                urlString = "${BASE_URL}/search"
            ) {
                parameter("q", query)
                parameter("autocomplete", 1)
                parameter("limit", LIMIT)
            }
        }
    }

    override suspend fun getAddressesByCoordinates(
        lat: Double,
        lon: Double
    ): Result<ApiResponseDto, DataError.Remote> {
        return safeCall<ApiResponseDto> {
            httpClient.get(
                urlString = "${BASE_URL}/reverse"
            ) {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("limit", 1)
            }
        }
    }
}