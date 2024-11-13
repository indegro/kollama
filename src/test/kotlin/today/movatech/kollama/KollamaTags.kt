package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaTags : KollamaTest() {

    @Test
    fun test_list_models() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/tags")
                require(request.method.value == "GET")

                respond(
                    content = ByteReadChannel(readResponse("data/list_models.json")),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val kollamaClient = KollamaClientImpl(
                engine = mockEngine
            )

            assertEquals(2, kollamaClient.tags().models.size)
        }
    }
}