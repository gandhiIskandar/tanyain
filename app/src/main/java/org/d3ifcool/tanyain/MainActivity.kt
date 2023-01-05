package org.d3ifcool.tanyain

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import android.app.AlertDialog
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import org.d3ifcool.tanyain.viewModels.FirebaseUserViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: FirebaseUserViewModel by viewModels()
    private lateinit var dialogLoading:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        dialogLoading = dialogLoading()

        dialogLoading.show()

        val content: View = findViewById(android.R.id.content)

        viewModel.authState.observe(this) {
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (it != null) {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                           Navigation.findNavController(
                                this@MainActivity, R.id.navHostFragment
                            ).navigate(R.id.loginFragment)

                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            false
                        }
                    }
                }
            )
        }


        getLocation()

    }


    @SuppressLint("MissingPermission")
    private fun setupListener() {

        val fusedProvider = LocationServices.getFusedLocationProviderClient(this)
        val mLocationRequest = LocationRequest.Builder(500)
        mLocationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        val locationListener = mLocationRequest.build()

        val callback = object: LocationCallback(){
            override fun onLocationAvailability(p0: LocationAvailability) {

            }

            override fun onLocationResult(mlocation: LocationResult) {
                for(location in mlocation.locations){
                    if(location != null){
                        fusedProvider.removeLocationUpdates(this)
                        viewModel.location = location

                        Log.d("locationa", location.latitude.toString())

                        dialogLoading.dismiss()
                    }
                }
            }
        }

        fusedProvider.requestLocationUpdates(locationListener ,callback, null)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            1000 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocation()

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLocation() {

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )

            return

        } else {

            LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener {
                if(it ==null){
                    setupListener()
                }else{
                    Log.d("locationb", it.longitude.toString())
                    viewModel.location = it
                    dialogLoading.dismiss()

                }
            }


        }
    }

    private fun dialogLoading(): AlertDialog {
        val builder = AlertDialog.Builder(this)

        builder.setView(R.layout.dialog_loading)

        val dialog = builder.create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    private fun requestTimeOut(){
        dialogLoading.dismiss()

        Toast.makeText(this,"Gagal mendapatkan lokasi, mohon coba lagi nanti", Toast.LENGTH_SHORT).show()

    }

}
