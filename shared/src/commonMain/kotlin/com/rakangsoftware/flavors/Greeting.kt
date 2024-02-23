package com.rakangsoftware.flavors

import Config

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! ${Config.type}"
    }
}