package com.izzaweb.dicodingfootbalapps.prevmatch

import com.izzaweb.dicodingfootbalapps.model.Events


interface PrevMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchLust(data: List<Events>)
}