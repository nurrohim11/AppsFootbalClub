package com.izzaweb.dicodingfootbalapps.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.google.gson.Gson
import com.izzaweb.dicodingfootbalapps.R
import com.izzaweb.dicodingfootbalapps.R.drawable.ic_add_to_favorites
import com.izzaweb.dicodingfootbalapps.R.drawable.ic_added_to_favorites
import com.izzaweb.dicodingfootbalapps.R.id.add_to_favorite
import com.izzaweb.dicodingfootbalapps.R.menu.detail_menu
import com.izzaweb.dicodingfootbalapps.api.ApiRepository
import com.izzaweb.dicodingfootbalapps.favorit.database
import com.izzaweb.dicodingfootbalapps.model.Events
import com.izzaweb.dicodingfootbalapps.model.Favorit
import com.izzaweb.dicodingfootbalapps.util.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException

class DetailActivity : AppCompatActivity(), DetailEventView {
    val client = OkHttpClient()
    private lateinit var presenter: DetailEventPresenter
    private lateinit var id: String
    private var idHome: String = ""
    private var idAway: String = ""
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var events: Events
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)
        val intent = intent
        id = intent.getStringExtra("id")
        idHome = intent.getStringExtra("idHome")
        idAway = intent.getStringExtra("idAway")
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailEventPresenter(this, request, gson)
        presenter.getDetailEvent(id)
        swipeRefresh.onRefresh {
            presenter.getDetailEvent(id)
        }
        val logo = arrayOf(idHome, idAway)
        getBadge(logo)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()

    }

    override fun showDetailEvent(data: List<Events>) {
        events = Events(data[0].idEvent,
                data[0].teamHomeName,
                data[0].teamAwayName,
                data[0].teamHomeScore,
                data[0].teamAwayScore,
                data[0].dateEvent,
                data[0].time,
                data[0].idHomeTeam,
                data[0].idAwayTeam,
                data[0].strHomeGoalDetails,
                data[0].strAwayGoalDetails,
                data[0].intHomeShots,
                data[0].intAwayShots,
                data[0].strHomeLineupDefense,
                data[0].strAwayLineupDefense,
                data[0].strHomeLineupMidfield,
                data[0].strAwayLineupMidfield,
                data[0].strHomeLineupForward,
                data[0].strAwayLineupForward,
                data[0].strHomeLineupSubstitutes,
                data[0].strAwayLineupSubstitutes,
                data[0].strHomeFormation,
                data[0].strAwayFormation,
                data[0].strHomeLineupGoalkeeper,
                data[0].strAwayLineupGoalkeeper)
        swipeRefresh.isRefreshing = false

        tv_date_detail.text = data[0].dateEvent?.dateFormat()
        tv_time_detail.text = data[0].time?.timeFormat()
        tv_home_detail.text = data[0].teamHomeName
        tv_away_detail.text = data[0].teamAwayName
        if(data[0].teamHomeScore.isNullOrEmpty() && data[0].teamAwayScore.isNullOrEmpty()){
            tv_skor_detail.text = "0 vs 0"
        }else{
            tv_skor_detail.text = data[0].teamHomeScore+"  vs  "+data[0].teamAwayScore
        }
        tv_home_formation.text = data[0].strHomeFormation
        tv_away_formation.text = data[0].strAwayFormation
        tv_home_goals.text = data[0].strHomeGoalDetails?.nullToEmpty()?.SplitRapi(";", ";\n")
        tv_away_goals.text = data[0].strAwayGoalDetails?.nullToEmpty()?.SplitRapi(";", ";\n")
        tv_home_shots.text = data[0].intHomeShots.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_shots.text = data[0].intAwayShots.nullToEmpty().SplitRapi(";", ";\n")
        tv_home_gk.text = data[0].strHomeLineupGoalkeeper.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_gk.text = data[0].strAwayLineupGoalkeeper.nullToEmpty().SplitRapi(";", ";\n")
        tv_home_def.text =data[0].strHomeLineupDefense.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_def.text =data[0].strAwayLineupDefense.nullToEmpty().SplitRapi(";", ";\n")
        tv_home_mid.text =data[0].strHomeLineupMidfield.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_mid.text =data[0].strAwayLineupMidfield.nullToEmpty().SplitRapi(";", ";\n")
        tv_home_forward.text = data[0].strHomeLineupForward.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_forward.text =data[0].strAwayLineupForward.nullToEmpty().SplitRapi(";", ";\n")
        tv_home_subs.text = data[0].strHomeLineupSubstitutes.nullToEmpty().SplitRapi(";", ";\n")
        tv_away_subs.text = data[0].strAwayLineupSubstitutes.nullToEmpty().SplitRapi(";", ";\n")
    }
    private fun getBadge(logo: Array<String>) {
        for(i in 0..1){
            val request = Request.Builder()
                     .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var a = response.body()?.string()
                    runOnUiThread {
                        run(){
                            var json = JSONObject(a)
                            var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")
                            if(i == 0) {
                                Picasso.get().load(badge).into(img_home)
                            }else{
                                Picasso.get().load(badge).into(img_away)
                            }
                        }
                    }
                }
            })
        }
    }
    private fun favoriteState(){
        database.use {
            val result = select(Favorit.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorit>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorit.TABLE_FAVORITE,
                        Favorit.TEAM_ID to events.idEvent,
                        Favorit.TEAM_HOME_NAME to events.teamHomeName,
                        Favorit.TEAM_AWAY_NAME to events.teamAwayName,
                        Favorit.TEAM_HOME_SCORE to events.teamHomeScore,
                        Favorit.TEAM_AWAY_SCORE to events.teamAwayScore,
                        Favorit.DATE_EVENT to events.dateEvent,
                        Favorit.TIME to events.time,
                        Favorit.ID_HOME_TEAM to events.idHomeTeam,
                        Favorit.ID_AWAY_TEAM to events.idAwayTeam,
                        Favorit.STR_HOME_GOAL_DETAIL to  events.strHomeGoalDetails,
                        Favorit.STR_AWAY_GOAL_DETAIL to events.strAwayGoalDetails,
                        Favorit.HOME_SHOTS to events.intHomeShots,
                        Favorit.AWAY_SHOTS to events.intAwayShots,
                        Favorit.HOME_LINEUP_DEFENSE to events.strHomeLineupDefense,
                        Favorit.AWAY_LINEUP_DEFENSE to events.strHomeLineupDefense,
                        Favorit.HOME_LINEUP_MIDFIELD to events.strHomeLineupMidfield,
                        Favorit.AWAY_LINEUP_MIDFIELD to events.strHomeLineupMidfield,
                        Favorit.HOME_LINEUP_FORWARD to events.strHomeLineupForward,
                        Favorit.AWAY_LINEUP_FORWARD to events.strAwayLineupForward,
                        Favorit.HOME_LINEUP_SUBSTITUTES to events.strHomeLineupSubstitutes,
                        Favorit.AWAY_LINEUP_SUBSTITUTES to events.strAwayLineupSubstitutes,
                        Favorit.HOME_LINEUP_FORMASI to events.strHomeFormation,
                        Favorit.AWAY_LINEUP_FORMASI to events.strAwayFormation,
                        Favorit.HOME_LINEUP_GOOLKEEPER to events.strHomeLineupGoalkeeper,
                        Favorit.AWAY_LINEUP_GOOLKEEPER to events.strAwayLineupGoalkeeper)
            }
            snackbar(swipeRefresh,"Addded to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorit.TABLE_FAVORITE, "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        }catch (e : SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

}
