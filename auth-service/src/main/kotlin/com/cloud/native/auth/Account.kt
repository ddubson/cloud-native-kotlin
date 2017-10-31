package com.cloud.native.auth

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Account(@Id @GeneratedValue val id: Long,
                   val username: String,
                   val password: String,
                   val active: Boolean)