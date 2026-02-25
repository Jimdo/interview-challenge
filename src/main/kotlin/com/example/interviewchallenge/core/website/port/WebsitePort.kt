package com.example.interviewchallenge.core.website.port

import arrow.core.Either
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteError
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface WebsitePort {
    fun findAll(pageable: Pageable): Page<WebsiteDomain>

    fun findById(id: UUID): Either<WebsiteError, WebsiteDomain>

    fun findByUserId(userId: UUID, pageable: Pageable): Page<WebsiteDomain>

    fun create(website: CreateWebsiteDomain): Either<WebsiteError, WebsiteDomain>

    fun delete(id: UUID): Either<WebsiteError, Unit>
}
