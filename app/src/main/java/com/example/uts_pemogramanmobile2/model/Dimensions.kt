package com.example.uts_pemogramanmobile2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Dimensions(
    @SerializedName("width")
    val width: Double? = 0.0,
    @SerializedName("height")
    val height: Double? = 0.0,
    @SerializedName("depth")
    val depth: Double? = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeValue(depth)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Dimensions> {
        override fun createFromParcel(parcel: Parcel): Dimensions = Dimensions(parcel)
        override fun newArray(size: Int): Array<Dimensions?> = arrayOfNulls(size)
    }
}