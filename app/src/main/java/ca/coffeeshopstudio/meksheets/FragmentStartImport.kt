package ca.coffeeshopstudio.meksheets

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.coffeeshopstudio.meksheets.databinding.FragmentMechDetailBinding
import ca.coffeeshopstudio.meksheets.models.Mech
import ca.coffeeshopstudio.meksheets.repositories.MechRepository
import ca.coffeeshopstudio.meksheets.views.ActivityMain

/**
 * A fragment representing a single Mech detail screen.
 * This fragment is either contained in a [MechListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class FragmentStartImport : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: Mech? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentMechDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private val dragListener = View.OnDragListener { v, event ->
//        if (event.action == DragEvent.ACTION_DROP) {
//            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
//            val dragData = clipDataItem.text
//            item = PlaceholderContent.ITEM_MAP[dragData]
//            updateContent()
//        }
//        true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = MechRepository.MECH_MAP[it.getString(ARG_ITEM_ID)]
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

        _binding = FragmentMechDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        //itemDetailTextView = binding.mechDetail

        updateContent()
        //rootView.setOnDragListener(dragListener)

        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.name

        // Show the placeholder content as text in a TextView.
        item?.let {
            itemDetailTextView.text = it.fileName
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}