package ir.smr.kazemi.uitestapp.ui.main.bottomSheetFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.smr.kazemi.uitestapp.R

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 1/27/21.
 */
class ListBottomSheetDialogFragment(private val itemClickListenerCallback: (item: String) -> Unit) :
    BottomSheetDialogFragment() {

    companion object {

        const val TAG = "ListBottomSheetDialogFragment"

    }

    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.list_btm_sheet, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // round top corner of card View
        v.findViewById<CardView>(R.id.bottomSheet).setBackgroundResource(R.drawable.round_rect)

        initListView()
    }


    private fun initListView() {

        // get list from resources
        val list = resources.getStringArray(R.array.sicknesses)

        // init adapter
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_list_sickness,
            R.id.txt_item_sickness,
            list
        )

        // inflate and init listView
        val listView = v.findViewById<ListView>(R.id.list_sickness)
        listView.isClickable = false
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->

            dismiss()

            itemClickListenerCallback(list[position])

        }

    }

}