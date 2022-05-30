package com.riteh.autoshare.data.dataholders

import com.google.gson.annotations.SerializedName

data class BrandInfo(
    @SerializedName("Make_ID") val Make_ID: String,
    @SerializedName("Make_Name") val Make_Name: String
)