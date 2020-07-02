package org.hazelcast.urlshrtn

import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

internal const val PREFIX = "https://hzlc.st/"

@Path("/")
class RestApi @Inject constructor(private val random: Random) {

    @POST
    @Path("/{url}")
    @Produces(MediaType.TEXT_PLAIN)
    fun shorten(@PathParam("url") url: String) =
        random
            .string()
            .prependIndent(PREFIX)
}