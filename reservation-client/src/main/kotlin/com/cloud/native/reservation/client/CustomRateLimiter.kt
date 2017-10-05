package com.cloud.native.reservation.client

import com.google.common.util.concurrent.RateLimiter
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

@Component
class CustomRateLimiter : ZuulFilter() {
    private val rateLimiter: RateLimiter = RateLimiter.create(29.0/30.0)

    override fun run(): Any {
        val currentContext: RequestContext = RequestContext.getCurrentContext()
        val response = currentContext.response

        if (!rateLimiter.tryAcquire()) {
            try {
                val status = HttpStatus.TOO_MANY_REQUESTS
                response.status = status.value()
                currentContext.setSendZuulResponse(false)
                throw ZuulException("couldn't proceed, too many requests",
                        status.value(), status.reasonPhrase)
            } catch (e: ZuulException) {
                ReflectionUtils.rethrowRuntimeException(e)
            }
        }

        return Any()
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "pre"

    override fun filterOrder(): Int = Ordered.HIGHEST_PRECEDENCE + 100
}