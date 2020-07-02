package org.hazelcast.urlshrtn

import javax.enterprise.context.ApplicationScoped
import kotlin.random.Random

@ApplicationScoped
object Random {

    private val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun string() =
        (0..10)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
}