package com.alexchar_dev.socialrelationships.domain.entity

import java.sql.Timestamp

class FriendRequest(val avatar: String, val timestamp: Timestamp, val uid: String, val username: String)