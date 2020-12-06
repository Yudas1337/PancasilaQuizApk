package com.yudas.pancasila_quiz_apk.retrofit

open class Value {

    var value: String? = null
        internal set
    var message: String? = null
        internal set
    var answer: List<Result>? = null
        internal set
    var question: List<Result>? = null
        internal set

    var isError: Boolean = false
}
