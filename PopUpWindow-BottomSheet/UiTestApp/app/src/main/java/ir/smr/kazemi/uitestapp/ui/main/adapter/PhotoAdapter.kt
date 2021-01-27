package ir.smr.kazemi.uitestapp.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.smr.kazemi.uitestapp.R
import kotlinx.android.synthetic.main.item_gallery_rec.view.*
import java.util.ArrayList

class PhotoAdapter(val list: ArrayList<Uri>) : RecyclerView.Adapter<PhotoAdapter.Vh>() {

    class Vh(val rec_item: View) : RecyclerView.ViewHolder(rec_item) {

        val imageView = rec_item.img

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_rec_preview, parent, false)

        return Vh(v)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.imageView.load(list[position]) {
            crossfade(true)
            placeholder(R.drawable.ic_photo_size_select_actual_24)
            error(R.drawable.ic_baseline_broken_image_24)
        }

    }

    override fun getItemCount(): Int = list.size

}
