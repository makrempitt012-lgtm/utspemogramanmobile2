package com.example.uts_pemogramanmobile2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product> = emptyList(),
    @SerializedName("total")
    val total: Int? = 0,
    @SerializedName("skip")
    val skip: Int? = 0,
    @SerializedName("limit")
    val limit: Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        products = parcel.createTypedArrayList(Product.CREATOR) ?: emptyList(),
        total = parcel.readValue(Int::class.java.classLoader) as? Int,
        skip = parcel.readValue(Int::class.java.classLoader) as? Int,
        limit = parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(products)
        parcel.writeValue(total)
        parcel.writeValue(skip)
        parcel.writeValue(limit)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ProductResponse> {
        override fun createFromParcel(parcel: Parcel): ProductResponse = ProductResponse(parcel)
        override fun newArray(size: Int): Array<ProductResponse?> = arrayOfNulls(size)
    }
}