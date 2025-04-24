package com.novelight.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.novelight.application.R
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleaseModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class ReleasesAdapter(private val mList: List<RanobeReleaseModel>, private val context: Context): RecyclerView.Adapter<ReleasesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.releases_cardview, parent, false)

        return ViewHolder(view)
    }

// binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val release = mList[position]

        // https://images.ranobedb.org/imageFilename

        if (!mList.isEmpty()) {
            val imageFilename = release.image.filename

            if (imageFilename != "") {
                Picasso.with(context)
                    .load("https://images.ranobedb.org/" + imageFilename)
                    .into(holder.releaseImage)
            }



            val volumeRegex = Regex("(?i)(Vol\\.?|vol\\.?|Volume) \\d+(-\\d+)?|\\d+$") // Covers "Vol.", "Vol", "vol.", "vol", "Volume", and standalone numbers
            val volumeMatch = volumeRegex.find(release.title)

            val cleanTitle = volumeMatch?.let {
                release.title.removeSuffix(", " + it.value).trim().removeSuffix(it.value).trim()
            } ?: release.title

            val cleanVolume = "Vol. " + (volumeMatch?.value?.replace(Regex("(?i)(Vol\\.?|vol\\.?|Volume) "), "") ?: "1")

            holder.releaseName.text = cleanTitle
            holder.releaseVolume.text = cleanVolume
            holder.releaseDate.text = formatDate(release.release_date)







        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val releaseName: TextView = itemView.findViewById(R.id.releaseTitle)
        val releaseImage: ImageView = itemView.findViewById(R.id.releaseImage)
        val releaseVolume: TextView = itemView.findViewById(R.id.releaseVolume)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
    }

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

}