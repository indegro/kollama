package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaGenerate : KollamaTest() {

    @Test
    fun test_generate_response_non_stream() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/generate")
                require(request.method.value == "POST")
                require(request.body is TextContent)

                val body = (request.body as TextContent).text.decodeFromString<GenerateRequest>().getOrThrow()
                require(!body.stream!!)

                respond(
                    content = ByteReadChannel(readResponse("data/generate_non_stream.json")),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val kollamaClient = KollamaClientImpl(
                engine = mockEngine
            )

            val generateRequest = GenerateRequest(
                model = CODE_GEMMA_2B.modelName,
                prompt = "Why is the sky blue?",
                stream = false,
            )
            assertEquals(
                "The sky is blue because it is the color of the sky.",
                kollamaClient.generate(generateRequest).response
            )
        }
    }
}