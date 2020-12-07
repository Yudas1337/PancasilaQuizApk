package com.yudas.pancasila_quiz_apk.retrofit

open class Value {

    var value: String? = null
        internal set
    var message: String? = null
        internal set
    var result: List<Result>? = null

    var isError: Boolean = false
}
