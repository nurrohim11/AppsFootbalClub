package com.izzaweb.dicodingfootbalapps

import com.izzaweb.dicodingfootbalapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider: CoroutineContextProvider(){
    override val main: CoroutineContext = Unconfined
}