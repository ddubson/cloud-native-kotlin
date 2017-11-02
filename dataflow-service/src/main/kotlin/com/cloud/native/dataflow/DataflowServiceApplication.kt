package com.cloud.native.dataflow

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.dataflow.server.EnableDataFlowServer

@EnableDiscoveryClient
@EnableDataFlowServer
@SpringBootApplication
class DataflowServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(DataflowServiceApplication::class.java, *args)
}