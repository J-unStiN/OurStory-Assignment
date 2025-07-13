package com.ourstory.assign.common

class ApiResponse<T> {
    var code: Int = 0
    var message: String? = null
    var data: T? = null

    constructor(code: Int, message: String?, data: T?) {
        this.code = code
        this.message = message
        this.data = data
    }

    constructor(code: Int, message: String?) : this(code, message, null)

    constructor(code: Int) : this(code, null, null)

    companion object {
        fun <T> success(message: String, data: T): ApiResponse<T> {
            return ApiResponse(200, message, data)
        }

        fun <T> error(message: String): ApiResponse<T> {
            return ApiResponse(500, message, null)
        }
    }
}