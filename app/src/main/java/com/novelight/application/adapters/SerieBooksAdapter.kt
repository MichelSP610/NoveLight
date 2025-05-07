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
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedBookViewModel

class SerieBooksAdapter(private val mList: List<RoomBook>, private val navController: NavController, private val selectedBookViewModel: SelectedBookViewModel): RecyclerView.Adapter<SerieBooksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.read_cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val book = mList[position]

        holder.bookTitle.text = book.title
        holder.bookReleaseDate.text = CustomUtils.getFormattedDateString(book.releaseDate)

        holder.bookLayout.setOnClickListener {
            selectedBookViewModel.setSelectedBookId(book.id)
            navController.navigate(R.id.action_seriesFragment2_to_bookFragment)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.title)
        val bookReleaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        val bookLayout: LinearLayout = itemView.findViewById(R.id.layout)
    }
}