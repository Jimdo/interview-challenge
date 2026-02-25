package com.example.interviewchallenge.core.websiteversion.port

import arrow.core.Either
import com.example.interviewchallenge.core.websiteversion.model.CreateWebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionError
import java.util.UUID

interface WebsiteVersionPort {
    fun findByWebsiteId(websiteId: UUID): List<WebsiteVersionDomain>

    fun findByWebsiteIdAndVersionNumber(websiteId: UUID, versionNumber: Int): Either<WebsiteVersionError, WebsiteVersionDomain>

    fun create(version: CreateWebsiteVersionDomain, versionNumber: Int): Either<WebsiteVersionError, WebsiteVersionDomain>
}
