package com.rakangsoftware.flavors

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform