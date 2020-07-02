package org.hazelcast.urlshrtn

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.hazelcast.HazelcastServerTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.core.StringStartsWith.startsWith
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(HazelcastServerTestResource::class)
class RestApiTest {

    private val urlParamName = "url"
    private val longUrl = "http://foo.bar"
    private val endpoint = "/{${urlParamName}}"

    @Test
    fun `POST should return response with HTTP status 200`() {
        whenPost()
        .then()
            .statusCode(200)
    }

    @Test
    fun `POST should return response whose content starts with the root url`() {
        whenPost()
        .then()
            .body(startsWith(PREFIX))
    }

    private fun whenPost() =
        given()
            .pathParam(urlParamName, longUrl)
        .`when`()
            .post(endpoint)
    }