package com.izzaweb.dicodingfootbalapps.favorit

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.izzaweb.dicodingfootbalapps.R
import com.izzaweb.dicodingfootbalapps.model.Favorit
import com.izzaweb.dicodingfootbalapps.util.dateFormat
import com.izzaweb.dicodingfootbalapps.util.timeFormat
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteAdapter(private val events:List<Favorit>, private  val listener: (Favorit) -> Unit) : RecyclerView.Adapter<FavoritViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        return FavoritViewHolder(NextMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }
    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }


    override fun getItemCount(): Int = events.size

}
class NextMatchUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout{
                lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                }
                gravity = R.id.center
                orientation = LinearLayout.VERTICAL
                background = ColorDrawable(Color.parseColor("#ffffff"))

                textView{
                    id = R.id.tvDateEvent
                    textSize = 16f
                    textColor = Color.GREEN
                }.lparams{
                    topMargin= dip(15)
                    gravity = Gravity.CENTER
                }
                textView{
                    id = R.id.tvTimeEvent
                    textSize = 14f
                    textColor = Color.GREEN
                }.lparams{
                    topMargin= dip(6)
                    gravity = Gravity.CENTER
                }

                relativeLayout{
                    lparams(width= matchParent, height = wrapContent){
                        padding= dip(16)
                    }
                    textView{
                        id = R.id.tvTeamHomeName
                        textSize = 18f
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.tvTeamHomeScore)
                    }

                    textView{
                        id = R.id.tvTeamHomeScore
                        textSize = 18f
                        visibility = View.GONE
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.textViewVs)
                    }

                    textView{
                        id = R.id.textViewVs
                        textSize= 16f
                    }.lparams{
                        centerHorizontally()
                    }

                    textView{
                        id = R.id.tvTeamAwayScore
                        textSize = 18f
                        visibility = View.GONE
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.textViewVs)
                    }

                    textView{
                        id = R.id.tvTeamAwayName
                        textSize = 18f
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.tvTeamAwayScore)
                    }
                }
            }
        }
    }
}
class FavoritViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvDate : TextView = view.find(R.id.tvDateEvent)
    private val tvVS : TextView = view.find(R.id.textViewVs)
    private val tvTime : TextView = view.find(R.id.tvTimeEvent)
    private val tvhomeTeam : TextView = view.find(R.id.tvTeamHomeName)
    private val tvawayTeam : TextView = view.find(R.id.tvTeamAwayName)
    private val tvHomeScore : TextView = view.find(R.id.tvTeamHomeScore)
    private val tvAwayScore : TextView = view.find(R.id.tvTeamAwayScore)

    fun bindItem(favorit: Favorit, listener: (Favorit) -> Unit) {

        tvDate.text = favorit.dateEvent?.dateFormat()
        tvhomeTeam.text = favorit.teamHomeName
        tvawayTeam.text = favorit.teamAwayName
        tvTime.text = favorit.time?.timeFormat()
        tvHomeScore.text= favorit.teamHomeScore
        tvAwayScore.text = favorit.teamAwayScore
        tvVS.text = "VS"
        itemView.onClick { listener(favorit) }
    }
}
