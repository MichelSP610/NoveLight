package com.novelight.application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.novelight.application.R
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedReleaseViewModel

class BookReleasesAdapter(private val mList: List<RoomRelease>, private val viewModel: SelectedReleaseViewModel, private val navController: NavController): RecyclerView.Adapter<BookReleasesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.read_cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val release = mList[position]

        holder.releaseTitle.text = release.title
        holder.releaseReleaseDate.text = CustomUtils.getFormattedDateString(release.release_date) + " - " + release.format.value

        holder.layout.setOnClickListener {
            viewModel.setSelectedReleaseId(release.id)
            navController.navigate(R.id.action_bookFragment_to_releaseFragment)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val releaseTitle: TextView = itemView.findViewById(R.id.title)
        val releaseReleaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        val layout: LinearLayout = itemView.findViewById(R.id.layout)
    }
}