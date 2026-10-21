package com.example.uts_pemogramanmobile2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("stock")
    val stock: Int = 0,
    @SerializedName("availabilityStatus")
    val availabilityStatus: String? = "In Stock",
    @SerializedName("tags")
    val tags: List<String>? = emptyList(),
    @SerializedName("dimensions")
    val dimensions: Dimensions? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = emptyList(),
    @SerializedName("rating")
    val rating: Double = 0.0,
    @SerializedName("images")
    val images: List<String>? = emptyList(),
    @SerializedName("thumbnail")
    val thumbnail: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        category = parcel.readString() ?: "",
        price = parcel.readDouble(),
        stock = parcel.readInt(),
        availabilityStatus = parcel.readString(),
        tags = parcel.createStringArrayList(),
        dimensions = parcel.readParcelable(Dimensions::class.java.classLoader),
        reviews = parcel.createTypedArrayList(Review.CREATOR),
        rating = parcel.readDouble(),
        images = parcel.createStringArrayList(),
        thumbnail = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeDouble(price)
        parcel.writeInt(stock)
        parcel.writeString(availabilityStatus)
        parcel.writeStringList(tags)
        parcel.writeParcelable(dimensions, flags)
        parcel.writeTypedList(reviews)
        parcel.writeDouble(rating)
        parcel.writeStringList(images)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product = Product(parcel)
        override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
    }
}