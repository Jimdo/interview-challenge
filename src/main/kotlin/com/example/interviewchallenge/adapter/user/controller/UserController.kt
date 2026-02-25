package com.example.interviewchallenge.adapter.user.controller

import com.example.interviewchallenge.adapter.user.controller.mapper.toDomain
import com.example.interviewchallenge.adapter.user.controller.mapper.toResponse
import com.example.interviewchallenge.adapter.user.controller.rest.UserCreateRequest
import com.example.interviewchallenge.adapter.user.controller.rest.UserListResponse
import com.example.interviewchallenge.adapter.user.controller.rest.UserReadResponse
import com.example.interviewchallenge.adapter.user.controller.rest.UserResponseError
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.usecase.CreateUserUseCase
import com.example.interviewchallenge.core.user.usecase.DeleteUserUseCase
import com.example.interviewchallenge.core.user.usecase.GetUserUseCase
import com.example.interviewchallenge.core.user.usecase.ListUsersUseCase
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
internal class UserController(
    private val listUsersUseCase: ListUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) {
    @GetMapping
    fun listUsers(
        @RequestParam(defaultValue = "0") @Valid @Min(0) page: Int,
        @RequestParam(defaultValue = "20") @Valid @Min(1) @Max(100) pageSize: Int,
    ): ResponseEntity<UserListResponse> {
        val result = listUsersUseCase(PageRequest.of(page, pageSize))
        return ResponseEntity.ok(
            UserListResponse.UserListSuccessResponse(
                users = result.map { it.toResponse() }.toList(),
                totalCount = result.totalElements,
                pageSize = result.size,
                page = result.number,
                totalPages = result.totalPages,
            ),
        )
    }

    @GetMapping("/{id}")
    fun getUser(
        @PathVariable id: UUID,
    ): ResponseEntity<UserReadResponse> =
        getUserUseCase(id).fold(
            {
                when (it) {
                    UserError.USER_NOT_FOUND ->
                        ResponseEntity.status(404).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.USER_NOT_FOUND),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.ok(it.toResponse()) },
        )

    @PostMapping(consumes = ["application/json"])
    fun createUser(
        @RequestBody @Valid request: UserCreateRequest,
    ): ResponseEntity<UserReadResponse> =
        createUserUseCase(request.toDomain()).fold(
            {
                when (it) {
                    UserError.PERSISTENCE_ERROR ->
                        ResponseEntity.status(400).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.BAD_REQUEST),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.status(201).body(it.toResponse()) },
        )

    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable id: UUID,
    ): ResponseEntity<Any> =
        deleteUserUseCase(id).fold(
            {
                when (it) {
                    UserError.USER_NOT_FOUND ->
                        ResponseEntity.status(404).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.USER_NOT_FOUND),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            UserReadResponse.UserErrorResponse(error = UserResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.noContent().build() },
        )
}
