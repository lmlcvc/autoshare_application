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
        // @Query("format") format: String
        @Path("brand") brand: String?
    ): ModelByBrandResponse

    /*@GET("myObjects/{id}")
    fun myObjectById(@Path("id") id: Int?, @Query("a_param") aParam: String?): Call<MyObject>*/

}