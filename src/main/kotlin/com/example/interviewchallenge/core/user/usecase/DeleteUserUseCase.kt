package com.example.interviewchallenge.core.user.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteUserUseCase(
    private val userPort: UserPort,
) {
    @Transactional
    operator fun invoke(id: UUID): Either<UserError, Unit> =
        userPort.delete(id)
}
