package com.novelight.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil3.compose.AsyncImage
import com.novelight.application.R
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.squareup.picasso.Picasso

class SeriesAdapter(private val mList: List<RanobeSerieModel>, private val context: Context): RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.noveles_cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val serie = mList[position]

        // https://images.ranobedb.org/imageFilename

        if (!mList.isEmpty()) {
            val imageFilename = serie.books.get(serie.books.lastIndex).image.filename

            if (imageFilename != "") {
                Picasso.with(context)
                    .load("https://images.ranobedb.org/" + imageFilename)
                    .into(holder.novelImage)
            }

            holder.novelName.text = serie.title
        }

        holder.novelLayout.setOnClickListener{
            Toast.makeText(context, serie.title, Toast.LENGTH_SHORT).show()
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val novelName: TextView = itemView.findViewById(R.id.novelName)
        val novelLayout: RelativeLayout = itemView.findViewById(R.id.novelLayout)
        val novelImage: ImageView = itemView.findViewById(R.id.novelImage)
    }
}