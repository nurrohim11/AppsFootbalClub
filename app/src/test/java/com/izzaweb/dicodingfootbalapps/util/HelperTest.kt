package com.izzaweb.dicodingfootbalapps.util

import junit.framework.Assert.assertEquals
import org.junit.Test

class HelperTest{
    @Test
    fun dateFormat (){
        val date = "2018-09-19"
        assertEquals("Wed, Sep 19 2018", date.dateFormat())
    }
    @Test
    fun timeFormat(){
        val time = "19:00:00"
        assertEquals("19:00", time.timeFormat())
    }
    @Test
    fun SplitRapi(){
        val dataEvent = "Mohammad Salah; Sadio Mane; Xherdan Shaqiri;"
        assertEquals("Mohammad Salah; \n Sadio Mane; \n  Xherdan Shaqiri;\n", dataEvent.SplitRapi(";",";\n"))
    }
    @Test
    fun  nullToEmpty(){
        val  nullString: String? = null
        assertEquals("", nullString.nullToEmpty())
    }
}