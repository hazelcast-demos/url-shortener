package org.hazelcast.urlshrtn

import com.hazelcast.core.HazelcastInstance
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

internal const val PREFIX = "https://hzlc.st/"

@Path("/")
class RestApi @Inject constructor(private val random: Random,
                                  private val client: HazelcastInstance) {

    private val urls
        get() = client.getMap<String, String>("urls")

    @POST
    @Path("/{url}")
    @Produces(MediaType.TEXT_PLAIN)
    fun shorten(@PathParam("url") url: String) =
        random
            .string()
            .apply {
                urls.set(this, url)
            }.prependIndent(PREFIX)

    @GET
    @Path("/{shortened}")
    @Produces(MediaType.TEXT_PLAIN)
    fun expand(@PathParam("shortened") shortened: String) =
        urls[shortened.removePrefix(PREFIX)]
}