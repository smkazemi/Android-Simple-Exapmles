package ir.smr.kazemi.uitestapp.ui.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Size
import android.view.*
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ir.smr.kazemi.flickrapiclient.extension.longToast
import ir.smr.kazemi.flickrapiclient.extension.shortToast
import ir.smr.kazemi.uitestapp.R
import ir.smr.kazemi.uitestapp.ui.main.adapter.PhotoAdapter
import ir.smr.kazemi.uitestapp.ui.main.bottomSheetFragment.GalleryBottomSheetDialogFragment
import ir.smr.kazemi.uitestapp.ui.main.bottomSheetFragment.ListBottomSheetDialogFragment
import ir.smr.kazemi.uitestapp.ui.main.viewModel.ViewModelFactoryMain
import ir.smr.kazemi.uitestapp.ui.main.viewModel.ViewModelMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list_btm_sheet.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var btmSheetGallery: GalleryBottomSheetDialogFragment
    private var photosList = ArrayList<Uri>()
    lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>

    lateinit var viewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelMain = ViewModelProvider(this, ViewModelFactoryMain(this))
            .get(ViewModelMain::class.java)

        initViews()

        loadGalleryPhotos()

    }

    private fun loadGalleryPhotos() {

        if (!isStoragePermissionGranted()) {

            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        } else {


            viewModelMain.getAllPhotos().observe(this) {

                photosList = ArrayList()
                photosList = it

            }
        }
    }

    // init layout views
    private fun initViews() {

        // init number of views
        txt_credit.text = String.format(getString(R.string.credit), 2)
        txt_counter.text = String.format(getString(R.string.txt_conversation_counter), 0)


        // attach btn click event
        img_file.setOnClickListener {

            showPopupWindow(it)
        }

        // btn question
        btn_questionType.setOnClickListener {
            showListSheet()
        }


        // Initializing the BottomSheetBehavior
        initListBottomSheet()


    }

    private fun initListBottomSheet() {

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

    }

    private fun showPopupWindow(anchor: View) {

        // first set dark window background
        setBackgroundAlpha(this, 0.5f)

        // init popUp window
        PopupWindow(this).apply {

            isFocusable = true

            val inflater = LayoutInflater.from(anchor.context)

            contentView = inflater.inflate(R.layout.popup_window, null).apply {
                measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
            }

            this.setBackgroundDrawable(null)
            this.width = ViewGroup.LayoutParams.MATCH_PARENT

            // event listener for attach image on card View
            contentView.findViewById<AppCompatImageView>(R.id.img_pop_file).setOnClickListener {

                // dismiss popUpWindow
                dismiss()

                // display gallery sheet
                showGallerySheet()
            }

        }.also { popupWindow ->
            // Absolute location of the anchor view
            val locationOfAnchorView = IntArray(2).apply {
                anchor.getLocationOnScreen(this)
            }

            val sizeOfPopUpLayout = Size(
                popupWindow.contentView.measuredWidth,
                popupWindow.contentView.measuredHeight
            )

            val sizeOfAnchorView = Size(
                anchor.measuredWidth,
                anchor.measuredHeight
            )

            // show pop up
            popupWindow.showAtLocation(
                anchor,
                Gravity.TOP or Gravity.START,
                locationOfAnchorView[0] - (sizeOfAnchorView.width * 6),
                locationOfAnchorView[1] - (sizeOfPopUpLayout.height + sizeOfAnchorView.height)
            )

            popupWindow.setOnDismissListener {
                setBackgroundAlpha(this, 1.0f)
            }

        }
    }

    private fun showGallerySheet() {

        if (isStoragePermissionGranted()) {

            btmSheetGallery = GalleryBottomSheetDialogFragment(photosList) { list ->

                initPreviewList(list)

                btmSheetGallery.dismiss()

            }.apply { show(supportFragmentManager, GalleryBottomSheetDialogFragment.TAG) }

        } else {
            longToast("Permission Denied, relaunch the App")
        }

    }

    // list of selected photos
    private fun initPreviewList(list: ArrayList<Uri>) {

        val photoAdapter = PhotoAdapter(list)

        // init recycler
        rec_preview.apply {

            setHasFixedSize(true)

            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

            adapter = photoAdapter

        }

        photoAdapter.notifyDataSetChanged()


    }

    private fun showListSheet() {

        // initialize list of sickness
        ListBottomSheetDialogFragment() {

            txt_sickness_title.text = it
            shortToast(it)

        }.apply {
            show(supportFragmentManager, ListBottomSheetDialogFragment.TAG)
        }

    }

    private fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {

        val lp = activity.window.attributes

        lp.alpha = bgAlpha

        if (bgAlpha == 1f) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        activity.window.attributes = lp
    }


    fun isStoragePermissionGranted(): Boolean {

        val ACCESS_EXTERNAL_STORAGE =

            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )

            return false

        }

        return true
    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->

            if (isGranted) {

                loadGalleryPhotos()


            } else {


            }

        }

}