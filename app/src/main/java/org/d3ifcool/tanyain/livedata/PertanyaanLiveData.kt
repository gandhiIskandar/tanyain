package org.d3ifcool.tanyain.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import org.d3ifcool.tanyain.data.Pertanyaan

class PertanyaanLiveData(
    private val db: DatabaseReference
) : LiveData<List<Pertanyaan>>() {

    private val data = ArrayList<Pertanyaan>()

    init {
        value = data.toList()
    }

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val pertanyaan = snapshot.getValue<Pertanyaan>() ?: return
            pertanyaan.id = snapshot.key ?: return
            data.add(pertanyaan)
            value = data.toList()
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {



            val pertanyaan = snapshot.getValue<Pertanyaan>() ?: return
            pertanyaan.id = snapshot.key ?: return
            val pos = data.indexOfFirst { it.id == pertanyaan.id }
            data[pos] = pertanyaan
            value = data.toList()
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val id = snapshot.key ?: return

            val pos = data.indexOfFirst { it.id == id }
            data.removeAt(pos)
            value = data.toList()
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onActive() {
        db.addChildEventListener(listener)
    }

    override fun onInactive() {
        db.removeEventListener(listener)
        data.clear()
    }
}