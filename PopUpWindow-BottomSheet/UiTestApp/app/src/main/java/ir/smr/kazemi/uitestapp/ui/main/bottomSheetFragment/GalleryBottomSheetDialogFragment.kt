package ir.smr.kazemi.uitestapp.ui.main.bottomSheetFragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.smr.kazemi.uitestapp.R
import ir.smr.kazemi.uitestapp.ui.main.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.gallery_btm_sheet.*
import kotlinx.android.synthetic.main.gallery_btm_sheet.view.*

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 1/27/21.
 */
class GalleryBottomSheetDialogFragment(
    val photosList: ArrayList<Uri>,
    private val itemClickListenerCallback: (list: ArrayList<Uri>) -> Unit
) :
    BottomSheetDialogFragment() {

    companion object {

        const val TAG = "GalleryBottomSheetDialogFragment"

    }

    private lateinit var galleryAdapter: GalleryAdapter
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.gallery_btm_sheet, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // round top corner of card View
        v.findViewById<CardView>(R.id.g_bottomSheet).setBackgroundResource(R.drawable.round_rect)

        initRecyclerView()

        v.btn_select.setOnClickListener {

            // return list of selected photos
            itemClickListenerCallback(
                galleryAdapter.selectedPhotoList
            )

        }

    }


    private fun initRecyclerView() {

        if (photosList[0] != Uri.EMPTY)
            photosList.add(0, Uri.EMPTY)

        galleryAdapter = GalleryAdapter(photosList)

        rec_gallery.apply {

            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = galleryAdapter

        }

        galleryAdapter.notifyDataSetChanged()


    }

}