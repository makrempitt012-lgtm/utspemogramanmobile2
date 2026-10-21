package com.example.uts_pemogramanmobile2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("rating")
    val rating: Int? = 0,
    @SerializedName("comment")
    val comment: String? = "",
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("reviewerName")
    val reviewerName: String? = "",
    @SerializedName("reviewerEmail")
    val reviewerEmail: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(rating)
        parcel.writeString(comment)
        parcel.writeString(date)
        parcel.writeString(reviewerName)
        parcel.writeString(reviewerEmail)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review = Review(parcel)
        override fun newArray(size: Int): Array<Review?> = arrayOfNulls(size)
    }
}