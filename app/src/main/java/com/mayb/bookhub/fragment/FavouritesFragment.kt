package com.mayb.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mayb.bookhub.R
import com.mayb.bookhub.adapter.FavouriteRecyclerAdapter
import com.mayb.bookhub.database.BookDatabase
import com.mayb.bookhub.database.BookEntity


class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourites:RecyclerView
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter:FavouriteRecyclerAdapter

    var dbBookList = listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerFavourites = view.findViewById(R.id.recyclerFavourites)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        // visibility of progress bar

        layoutManager = GridLayoutManager(activity as Context,2)

        dbBookList = RetrieveFavourites(activity as Context).execute().get()
        if(activity!=null){
            progressLayout.visibility=View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourites.adapter = recyclerAdapter
            recyclerFavourites.layoutManager = layoutManager
        }

        return view
    }

     class RetrieveFavourites(val context:Context): AsyncTask<Void, Void, List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBooks()
        }
    }





}