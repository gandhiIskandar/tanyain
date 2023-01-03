package org.d3ifcool.tanyain.viewModels

import androidx.lifecycle.ViewModel
import org.d3ifcool.tanyain.livedata.FirebaseUserLiveData

class FirebaseUserViewModel: ViewModel() {
    val authState = FirebaseUserLiveData()
}