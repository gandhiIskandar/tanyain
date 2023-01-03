package org.d3ifcool.tanyain.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity
data class Pertanyaan(
    @get:Exclude
    var id: String = "",
    var userId: String = "",
    var pertanyaan: String = "",
)
