package today.movatech.kollama.models

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import today.movatech.kollama.CreateRequest
import today.movatech.kollama.KollamaClientImpl
import today.movatech.kollama.KollamaTest
import today.movatech.kollama.ProgressResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaCreate : KollamaTest() {

    @Test
    fun test_create_model() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/create")
                require(request.method.value == "POST")
                require(request.body is TextContent)

                respond(
                    content = ByteReadChannel(readResponse("data/create.jsonl")),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val kollamaClient = KollamaClientImpl(
                engine = mockEngine
            )

            val createRequest = CreateRequest(
                model = "model",
                modelfile = "model_file"
            )

            val createResponse = mutableListOf<ProgressResponse>()
            kollamaClient.create(createRequest).toList(createResponse)
            assertEquals(11, createResponse.size)
        }
    }
}