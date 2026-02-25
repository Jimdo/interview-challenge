package com.example.interviewchallenge.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.example.interviewchallenge"])
@EnableJpaRepositories(basePackages = ["com.example.interviewchallenge.adapter"])
@EntityScan(basePackages = ["com.example.interviewchallenge.adapter"])
@EnableJpaAuditing
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
