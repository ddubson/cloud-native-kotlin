package com.cloud.native.auth

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountUserDetailsService(val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return this.accountRepository.findByUsername(username)
                .map {
                    User.withUsername(it.username).password(it.password)
                            .roles("USER").build()
                }.orElseThrow { UsernameNotFoundException("couldn't find $username") }
    }
}