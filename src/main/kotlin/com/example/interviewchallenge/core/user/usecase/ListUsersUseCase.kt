package com.example.interviewchallenge.core.user.usecase

import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.port.UserPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ListUsersUseCase(
    private val userPort: UserPort,
) {
    operator fun invoke(pageable: Pageable): Page<UserDomain> =
        userPort.findAll(pageable)
}
