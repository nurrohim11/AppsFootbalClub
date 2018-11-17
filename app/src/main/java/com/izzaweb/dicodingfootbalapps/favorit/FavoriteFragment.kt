package com.izzaweb.dicodingfootbalapps.favorit


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.izzaweb.dicodingfootbalapps.R
import com.izzaweb.dicodingfootbalapps.detail.DetailActivity
import com.izzaweb.dicodingfootbalapps.model.Favorit
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteFragment : Fragment() , AnkoComponent<Context>{
    private var favorites: MutableList<Favorit> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var listFavorit: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteAdapter(favorites) {
            ctx.startActivity<DetailActivity>(
                    "id" to "${it.teamId}",
                    "idHome" to "${it.idHomeTeam}",
                    "idAway" to "${it.idAwayTeam}"
            )
        }
        listFavorit.adapter = adapter
        showFavorite()
        swipe.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout{
            lparams(width = matchParent, height = wrapContent)
            orientation= LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            background = ColorDrawable(Color.parseColor("#f6f6f6"))

            swipe = swipeRefreshLayout{
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_blue_light,
                        android.R.color.holo_green_light,
                        android.R.color.holo_green_dark)
                relativeLayout{
                    lparams(width = matchParent, height = wrapContent)

                    listFavorit = recyclerView{
                        id = R.id.listPrevMatch
                        lparams(width= matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            swipe.isRefreshing = false
            val result = select(Favorit.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorit>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}