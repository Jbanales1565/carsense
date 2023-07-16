package com.example.final_part2

import java.io.Serializable

data class bt_devices(
    var showID: Long? = 0L,
    var showName: String? = "",
    var showChannel: String? = "",
    var showGenre: String? = "",
    var showLanguage: String? = ""
) : Serializable