package com.riteh.autoshare.network

import com.riteh.autoshare.data.dataholders.ModelByBrandResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BrandModelApi {

    companion object {
        const val BASE_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/"
    }

    @GET("getmodelsformake/{brand}?format=json")
    suspend fun getModelsByBrand(
        @Path("brand") brand: String?
    ): ModelByBrandResponse
}