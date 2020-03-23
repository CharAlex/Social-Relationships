package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class SearchUserCaseImpl(private val userRepository: UserRepository) : SearchUserCase{

}

interface SearchUserCase {

}