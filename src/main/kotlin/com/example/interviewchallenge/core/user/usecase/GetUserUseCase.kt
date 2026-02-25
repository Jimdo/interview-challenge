package com.example.interviewchallenge.core.user.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetUserUseCase(
    private val userPort: UserPort,
) {
    operator fun invoke(id: UUID): Either<UserError, UserDomain> =
        userPort.findById(id)
}
