package ca.coffeeshopstudio.meksheets

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import ca.coffeeshopstudio.meksheets.databinding.ActivityMechDetailBinding
import ca.coffeeshopstudio.meksheets.models.Mech
import ca.coffeeshopstudio.meksheets.repositories.MechRepository
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import ca.coffeeshopstudio.meksheets.viewmodels.MechViewModel
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ActivityMainHost : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MechViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMechDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_mech_detail) as NavHostFragment


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect {
//                    // Update UI elements
//                }
            }
        }



        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_mech_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun displayMessage(helpMsg: Int) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.alert_dialog, null)
        val msg = view.findViewById<TextView>(R.id.textMsg)
        msg.text = Html.fromHtml(getString(helpMsg))
        val alertDialog = AlertDialog.Builder(this)
        //alertDialog.setTitle(R.string.help_title);
        alertDialog.setView(view)
        alertDialog.setPositiveButton(android.R.string.ok, null)
        val alert = alertDialog.create()
        alert.show()
    }
}