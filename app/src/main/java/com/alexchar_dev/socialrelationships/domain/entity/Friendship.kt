package com.alexchar_dev.socialrelationships.domain.entity

class Friendship(
    val uid: String = "", val username: String = "", val avatar: String = "",
    val love: Int = 50, val connection: Int = 50, val understanding: Int = 50,
    val trust: Int = 50, val respect: Int = 50, val compromise: Int = 50,
    val support: Int = 50, val amusement: Int = 50
    )