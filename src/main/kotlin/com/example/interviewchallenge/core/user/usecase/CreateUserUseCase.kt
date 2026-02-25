package com.example.interviewchallenge.core.user.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserUseCase(
    private val userPort: UserPort,
) {
    @Transactional
    operator fun invoke(createUser: CreateUserDomain): Either<UserError, UserDomain> =
        userPort.create(createUser)
}
