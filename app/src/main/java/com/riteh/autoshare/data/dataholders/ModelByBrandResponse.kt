package com.riteh.autoshare.data.dataholders

data class ModelByBrandResponse(
    val Count: Int,
    val Message: String,
    val Results: MutableList<ModelInfo>,
    val SearchCriteria: String
)