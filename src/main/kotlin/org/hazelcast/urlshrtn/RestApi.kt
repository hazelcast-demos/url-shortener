package org.hazelcast.urlshrtn

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.map.listener.EntryAddedListener
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

internal const val PREFIX = "https://hzlc.st/"

@Path("/")
class RestApi @Inject constructor(private val random: Random,
                                  private val client: HazelcastInstance) {

    init {
        shorteneds.addEntryListener(EntryAddedListener<String, String> {
            urls.set(it.value, it.key)
        }, true)
    }

    private val shorteneds
        get() = client.getMap<String, String>("shorteneds")

    private val urls
        get() = client.getMap<String, String>("urls")

    @POST
    @Path("/{url}")
    @Produces(MediaType.TEXT_PLAIN)
    fun shorten(@PathParam("url") url: String) =
        with(random.string()) {
            shorteneds.putIfAbsent(url, this) ?: this
        }.prependIndent(PREFIX)

    @GET
    @Path("/{shortened}")
    @Produces(MediaType.TEXT_PLAIN)
    fun expand(@PathParam("shortened") shortened: String) =
        urls[shortened.removePrefix(PREFIX)]
}