package com.yudas.pancasila_quiz_apk.retrofit

open class Value {

    var value: String? = null
        internal set
    var message: String? = null
        internal set
    var question: ArrayList<Soal>? = null
        internal set

    var ranking: ArrayList<Ranking>? = null
        internal set

    var isError: Boolean = false
}
