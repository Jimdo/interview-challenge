package com.example.interviewchallenge.adapter.user.persistence.repository

import com.example.interviewchallenge.adapter.user.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, UUID>
