package org.d3ifcool.tanyain.viewModels

import android.location.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import org.d3ifcool.tanyain.livedata.FirebaseUserLiveData

class FirebaseUserViewModel: ViewModel() {

    var location: Location? = null

    val authState = FirebaseUserLiveData()
}