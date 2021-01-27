package ir.smr.kazemi.uitestapp.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.smr.kazemi.uitestapp.R
import kotlinx.android.synthetic.main.item_gallery_rec.view.*

class GalleryAdapter(val listOfPhotos: ArrayList<Uri>) :
    RecyclerView.Adapter<GalleryAdapter.Vh>() {

    val selectedPhotoList = ArrayList<Uri>()

    private val markedPhotoPosition = mutableListOf<Int>()

    class Vh(val rec_item: View) : RecyclerView.ViewHolder(rec_item) {

        val imageView = rec_item.img
        val image_mark = rec_item.img_mark
        val image_cam = rec_item.img_camera

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_rec, parent, false)

        return Vh(v)

    }

    override fun getItemCount(): Int = listOfPhotos.size

    override fun onBindViewHolder(holder: Vh, position: Int) {

        // init items
        holder.imageView.apply {

            if (markedPhotoPosition.contains(position)) {

                if (holder.image_mark.visibility == View.VISIBLE) {
                    holder.image_mark.visibility = View.GONE
                } else {
                    holder.image_mark.visibility = View.VISIBLE
                }

            } else {

                holder.image_mark.visibility = View.GONE

            }

        }

        if (position == 0) {


            holder.imageView.visibility = View.GONE
            holder.image_cam.visibility = View.VISIBLE

            holder.imageView.setOnClickListener {

                if (holder.imageView.visibility == View.GONE && holder.image_cam.visibility == View.VISIBLE
                ) {
                    // TODO :
                }
            }

        } else {

            // hide camera icon for other images
            holder.imageView.visibility = View.VISIBLE
            holder.image_cam.visibility = View.GONE

            holder.imageView.load(listOfPhotos[position]){
                crossfade(true)
                placeholder(R.drawable.ic_photo_size_select_actual_24)
                error(R.drawable.ic_baseline_broken_image_24)
            }

            holder.imageView.setOnClickListener {

                markPhoto(holder, position)


            }


        }
    }

    private fun markPhoto(holder: Vh, position: Int) {

        if (holder.image_mark.visibility == View.VISIBLE) {
            holder.image_mark.visibility = View.GONE
            selectedPhotoList.remove(listOfPhotos[position])
        } else {
            holder.image_mark.visibility = View.VISIBLE
            selectedPhotoList.add(listOfPhotos[position])
        }


        markedPhotoPosition.add(position)

    }
}

