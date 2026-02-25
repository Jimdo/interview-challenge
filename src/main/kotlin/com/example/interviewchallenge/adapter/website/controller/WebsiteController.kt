package com.example.interviewchallenge.adapter.website.controller

import com.example.interviewchallenge.adapter.website.controller.mapper.toDomain
import com.example.interviewchallenge.adapter.website.controller.mapper.toResponse
import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteCreateRequest
import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteListResponse
import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteReadResponse
import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteResponseError
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.usecase.CreateWebsiteUseCase
import com.example.interviewchallenge.core.website.usecase.DeleteWebsiteUseCase
import com.example.interviewchallenge.core.website.usecase.GetWebsiteUseCase
import com.example.interviewchallenge.core.website.usecase.ListWebsitesUseCase
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
@RequestMapping("/api/websites")
internal class WebsiteController(
    private val listWebsitesUseCase: ListWebsitesUseCase,
    private val getWebsiteUseCase: GetWebsiteUseCase,
    private val createWebsiteUseCase: CreateWebsiteUseCase,
    private val deleteWebsiteUseCase: DeleteWebsiteUseCase,
) {
    @GetMapping
    fun listWebsites(
        @RequestParam(defaultValue = "0") @Valid @Min(0) page: Int,
        @RequestParam(defaultValue = "20") @Valid @Min(1) @Max(100) pageSize: Int,
        @RequestParam(required = false) userId: UUID? = null,
    ): ResponseEntity<WebsiteListResponse> {
        val result = listWebsitesUseCase(PageRequest.of(page, pageSize), userId)
        return ResponseEntity.ok(
            WebsiteListResponse.WebsiteListSuccessResponse(
                websites = result.map { it.toResponse() }.toList(),
                totalCount = result.totalElements,
                pageSize = result.size,
                page = result.number,
                totalPages = result.totalPages,
            ),
        )
    }

    @GetMapping("/{id}")
    fun getWebsite(
        @PathVariable id: UUID,
    ): ResponseEntity<WebsiteReadResponse> =
        getWebsiteUseCase(id).fold(
            {
                when (it) {
                    WebsiteError.WEBSITE_NOT_FOUND ->
                        ResponseEntity.status(404).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.WEBSITE_NOT_FOUND),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.ok(it.toResponse()) },
        )

    @PostMapping(consumes = ["application/json"])
    fun createWebsite(
        @RequestBody @Valid request: WebsiteCreateRequest,
    ): ResponseEntity<WebsiteReadResponse> =
        createWebsiteUseCase(request.toDomain()).fold(
            {
                when (it) {
                    WebsiteError.USER_NOT_FOUND ->
                        ResponseEntity.status(404).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.USER_NOT_FOUND),
                        )
                    WebsiteError.PERSISTENCE_ERROR ->
                        ResponseEntity.status(400).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.BAD_REQUEST),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.status(201).body(it.toResponse()) },
        )

    @DeleteMapping("/{id}")
    fun deleteWebsite(
        @PathVariable id: UUID,
    ): ResponseEntity<Any> =
        deleteWebsiteUseCase(id).fold(
            {
                when (it) {
                    WebsiteError.WEBSITE_NOT_FOUND ->
                        ResponseEntity.status(404).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.WEBSITE_NOT_FOUND),
                        )
                    else ->
                        ResponseEntity.status(500).body(
                            WebsiteReadResponse.WebsiteErrorResponse(error = WebsiteResponseError.SERVER_ERROR),
                        )
                }
            },
            { ResponseEntity.noContent().build() },
        )
}
