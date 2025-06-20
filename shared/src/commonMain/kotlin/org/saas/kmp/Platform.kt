package org.saas.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform