package com.example.assestment02.model


import com.google.gson.annotations.SerializedName

class songR (
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songItems: List<songItem>
)
