package ca.coffeeshopstudio.meksheets

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import ca.coffeeshopstudio.meksheets.databinding.FragmentStartImportBinding
import ca.coffeeshopstudio.meksheets.models.Mech
import ca.coffeeshopstudio.meksheets.repositories.MechRepository
import ca.coffeeshopstudio.meksheets.viewmodels.MechViewModel
import ca.coffeeshopstudio.meksheets.views.ActivityMain

/**
 * A fragment representing a single Mech detail screen.
 * This fragment is either contained in a [FragmentMechList]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class FragmentStartImport : Fragment() {
    private var item: Mech? = null
    private var btnLoad: ImageButton? = null
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentStartImportBinding? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: MechViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultData: Intent? = result.data
                    val uri: Uri? = resultData?.data
                    viewModel.addMech(uri!!, requireContext())
                }
            }
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = viewModel.mechSet.value?.get(it.getString(ARG_ITEM_ID))
                if (item != null) {
                    startActivity(Intent(requireContext(), ActivityMain::class.java))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartImportBinding.inflate(inflater, container, false)
        val rootView = binding.root
        btnLoad = binding.btnLoad
        toolbarLayout = binding.toolbarLayout
        updateContent()
        return rootView
    }

    //The fragment argument representing the item ID that this fragment represents.
    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.name

        btnLoad?.setOnClickListener {
            performFileSearch()
        }
    }

    private fun performFileSearch() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/octet-stream"
        resultLauncher.launch(intent)

    }
}