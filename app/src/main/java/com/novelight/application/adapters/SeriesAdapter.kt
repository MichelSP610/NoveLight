package com.novelight.application.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.novelight.application.R
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.fragments.mainfragments.ExploreFragment
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedSerieViewModel
import com.squareup.picasso.Picasso
import io.ktor.util.reflect.instanceOf

class SeriesAdapter(private val mList: List<RoomSerie>, private val fragment: Fragment, private val viewModel: SelectedSerieViewModel): RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {
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
            val imageFilename = serie.imageFileName

            if (imageFilename != "") {
                CustomUtils.loadRanobeImageOnImageView(holder.novelImage, imageFilename, fragment.requireContext())
            }

            holder.novelName.text = serie.title
        }

        holder.novelLayout.setOnClickListener{
//            viewModel.setSelectedSerie(serie)
            viewModel.setSelectedSerieId(serie.id)
            if (fragment.instanceOf(ExploreFragment::class)) {
                fragment.findNavController().navigate(R.id.action_exploreFragment_to_seriesFragment2)
            } else {
                fragment.findNavController().navigate(R.id.action_libraryFragment_to_seriesFragment2)
            }
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