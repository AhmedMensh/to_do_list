package com.example.to_do_list.data.remote.entities

import com.google.gson.annotations.SerializedName

class RemoteTask (
        @SerializedName("id") val id: Int? = null,
        @SerializedName("name") val name: String,
        @SerializedName("parent_id") val parentId: Int? = null,
        @SerializedName("description") val description: String? = null,
        @SerializedName("start_date") val startDate: String,
        @SerializedName("end_date") val endDate: String,
        @SerializedName("is_completed") val isCompleted: Boolean = false,
        @SerializedName("priority") val priority: Int = 0,
        @SerializedName("level") val level: Int = 0
    )
