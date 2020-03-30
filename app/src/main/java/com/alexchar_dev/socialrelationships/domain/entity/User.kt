package com.alexchar_dev.socialrelationships.domain.entity

data class User(val userId: String = "",  val email: String = "", val username: String = "", var isVerified: Boolean = false, var requestIds: List<String> = emptyList())