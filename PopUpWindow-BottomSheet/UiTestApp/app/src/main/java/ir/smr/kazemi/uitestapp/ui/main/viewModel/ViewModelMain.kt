package ir.smr.kazemi.uitestapp.ui.main.viewModel

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.smr.kazemi.flickrapiclient.extension.toLog
import ir.smr.kazemi.uitestapp.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 1/27/21.
 */
class ViewModelMain(private val activity: MainActivity) : ViewModel() {

    private val imageList = MutableLiveData<ArrayList<Uri>>()

    // get all images from external storage
    private fun loadGalleryPhotos() {

        viewModelScope.launch(Dispatchers.IO) {

            val projection = arrayOf(MediaStore.MediaColumns._ID)
            val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI


            val cursor: Cursor? = activity.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )
            if (cursor != null) {

                val list = ArrayList<Uri>()

                while (cursor.moveToNext()) {

                    val columnIndexID: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                    val imageId = cursor.getLong(columnIndexID)
                    val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)

                    list.add(uriImage)
                }

                imageList.postValue(list)


            } else {
                "null".toLog()
            }
            cursor?.close()
        }
    }

    fun getAllPhotos(): LiveData<ArrayList<Uri>> {

        loadGalleryPhotos()

        return imageList
    }

}