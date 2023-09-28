package com.kyu9.accountbook.common

interface DataBridgeAble<RDB, ES> {
    fun toES(): ES
    fun toRDB(): RDB
}