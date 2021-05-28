package com.example.edlink.model

import android.net.Uri

class Posting(
    var id: String, var userId: String,
    var fullname: String, var date: String,
    var contentText: String, var contentImage: String
) {

    constructor() : this("", "", "", "", "", "") {

    }
}