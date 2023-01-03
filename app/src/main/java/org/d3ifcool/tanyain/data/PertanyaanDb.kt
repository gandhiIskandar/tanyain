package org.d3ifcool.tanyain.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.tanyain.livedata.PertanyaanLiveData

//@Database(entities = [Pertanyaan::class], version = 1, exportSchema = false)
//abstract class PertanyaanDb : RoomDatabase() {
//    abstract val dao: PertanyaanDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: PertanyaanDb? = null
//
//        fun getInstance(context: Context): PertanyaanDb {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        PertanyaanDb::class.java,
//                        "pertanyaan.db"
//                    ).build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}

class PertanyaanDb private constructor() {

    private val database = FirebaseDatabase.getInstance().getReference(PATH)
    val dao = object : PertanyaanDao {
        override fun insertData(pertanyaan: Pertanyaan) {
            database.push().setValue(pertanyaan)
        }

        override fun getData(): LiveData<List<Pertanyaan>> {
            return PertanyaanLiveData(database)
        }

        override fun deleteData(pertanyaanId: String) {
            database.child(pertanyaanId).removeValue()
        }

        override fun updateData(pertanyaan: Pertanyaan) {
            database.child(pertanyaan.id).setValue(pertanyaan)
        }

    }

    companion object {
        private const val PATH = "pertanyaan"

        @Volatile
        private var INSTANCE: PertanyaanDb? = null

        fun getInstance(): PertanyaanDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = PertanyaanDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}