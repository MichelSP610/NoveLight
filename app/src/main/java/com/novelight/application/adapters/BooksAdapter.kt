package com.novelight.application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.novelight.application.R
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.utils.CustomUtils

class BooksAdapter(private val mList: List<RanobeBook>): RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.books_cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val book = mList[position]

        holder.bookTitle.text = book.title
        holder.bookReleaseDate.text = CustomUtils.getFormattedDateString(book.c_release_date)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val bookNumber: TextView = itemView.findViewById(R.id.bookNumber)
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookReleaseDate: TextView = itemView.findViewById(R.id.bookReleaseDate)
        val bookLayout: LinearLayout = itemView.findViewById(R.id.bookLayout)
    }
}