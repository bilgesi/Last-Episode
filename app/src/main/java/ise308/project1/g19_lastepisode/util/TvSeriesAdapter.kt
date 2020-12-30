/*
 * Created by Berk Orhan on 7.12.2020
 * APPBeta Mobile | www.APPBeta.net | mobile@appbeta.net
 *
 *TODO           YASAL UYARI
 *
 *                 Bu proje dosyasının herhangi bir amaç ile izinsiz olarak dağıtılması,
 *                 çoğaltılması, satılması ve izinsiz kullanılması yasaktır!
 *
 *                 İzinsiz kullanım, çoğaltımı, dağıtımı, satışı yapıldığı
 *                 tespit edilmesi halinde yasal yollara başvurulacaktır.
 *
 *                  Copyright (c)  2020. Berk Orhan. All rights reserved.
 *
 */



package ise308.project1.g19_lastepisode.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ise308.project1.g19_lastepisode.MainActivity
import ise308.project1.g19_lastepisode.R

class TvSeriesAdapter(
    private val mainActivity: MainActivity, private val seriesList: List<TvSeries>): RecyclerView.Adapter<TvSeriesAdapter.ListItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_series, parent, false)

        return ListItemHolder(itemView)

    }

    override fun getItemCount(): Int {

        return seriesList.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {

            // Position value works like note ID.
            // We do not add any id value on JSON file, so when we want to find a tv series.
            // We use position value instead of series ID.
            val series = seriesList[position]

            holder.title.text = series.sName // Set Title
            holder.genre.text = series.sGenre // Set Title

            // Check Note Status
            if (series.isFinished) {
                // Set Finished Image
                holder.isFinishedImage.setImageResource(R.drawable.finished) // Set Not Finished Image
            } else {
                holder.isFinishedImage.setImageResource(R.drawable.notfinished) // Set Not Finished Image
            }


    }

    inner class ListItemHolder(view: View) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        internal var title = view.findViewById<View>(R.id.tvSName) as TextView

        internal var genre = view.findViewById<View>(R.id.tvSGenre) as TextView

        internal var isFinishedImage = view.findViewById<View>(R.id.isFinishedImage) as ImageView

        init {
                view.isClickable = true
                view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            // When user click a item,
            // We are sending item position to Show Series function in MainActivity
            mainActivity.showTVSeries(adapterPosition)
        }

    }
}