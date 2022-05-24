package com.riteh.autoshare.data.dataholders

import com.google.gson.annotations.SerializedName

data class ModelInfo(
    @SerializedName("Make_ID") val Make_ID: Int,
    @SerializedName("Make_Name") val Make_Name: String,
    @SerializedName("Model_ID") val Model_ID: Int,
    @SerializedName("Model_Name") var Model_Name: String
)