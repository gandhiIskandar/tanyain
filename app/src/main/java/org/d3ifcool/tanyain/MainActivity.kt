package org.d3ifcool.tanyain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import org.d3ifcool.tanyain.viewModels.FirebaseUserViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: FirebaseUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

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
    }
}
