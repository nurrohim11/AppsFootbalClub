package com.izzaweb.dicodingfootbalapps.nextmatch

import com.izzaweb.dicodingfootbalapps.model.Events

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchLust(data: List<Events>)
}