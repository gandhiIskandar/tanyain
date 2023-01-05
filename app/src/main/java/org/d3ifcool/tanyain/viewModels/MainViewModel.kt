package org.d3ifcool.tanyain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.tanyain.data.Pertanyaan
import org.d3ifcool.tanyain.data.PertanyaanDao
import org.d3ifcool.tanyain.data.PertanyaanDb

class MainViewModel() : ViewModel() {

    private val db = PertanyaanDb.getInstance().dao

    val data = db.getData()

    fun insertData(pertanyaan: Pertanyaan) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                db.insertData(pertanyaan)
            }
        }
    }

    fun deleteData(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.deleteData(id)
            }
        }
    }

    fun updateData(pertanyaan: Pertanyaan) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.updateData(pertanyaan)
            }
        }
    }
}