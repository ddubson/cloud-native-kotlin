package com.cloud.native.auth

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
class AuthServiceApplication(val accountRepository: AccountRepository): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        arrayOf("jdoe,test123", "jsmith,passw0rd").map { it.split(",") }.forEach {
            accountRepository.save(Account(0,it[0],it[1],true))
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(AuthServiceApplication::class.java, *args)
}