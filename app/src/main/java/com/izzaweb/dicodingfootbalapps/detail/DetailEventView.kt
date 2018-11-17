package com.izzaweb.dicodingfootbalapps.detail

import com.izzaweb.dicodingfootbalapps.model.Events

interface DetailEventView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: List<Events>)
}