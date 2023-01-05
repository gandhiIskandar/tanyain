package org.d3ifcool.tanyain

import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.d3ifcool.tanyain.databinding.FragmentMapsBinding
import org.d3ifcool.tanyain.viewModels.FirebaseUserViewModel
import java.util.*

class MapsFragment : Fragment() {

    private lateinit var gMap:GoogleMap

    private val viewModel:FirebaseUserViewModel by activityViewModels()

    private lateinit var binding:FragmentMapsBinding

    private lateinit var location: Location


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         *
         *
         */
        location = viewModel.location!!

       gMap = googleMap

        val mylatlong =LatLng(location.latitude,location.longitude)

        geocoder(location)

        val marker = MarkerOptions().position(LatLng(location.latitude,location.longitude)).title("Disini lokasi saya")

        gMap.addMarker(marker)

        gMap.animateCamera(CameraUpdateFactory.newLatLng(mylatlong))

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlong, 15f))



    }

private fun geocoder(latlong:Location){

    if(Geocoder.isPresent()) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        val alamat = geocoder.getFromLocation(latlong.latitude, latlong.longitude, 1)

        binding.lokasiPlayer.text = alamat[0].getAddressLine(0)
    }else{

        Toast.makeText(requireContext(), "Tidak dapat menampilkan alamat, mohon restart device anda",Toast.LENGTH_SHORT).show()
    }


}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}