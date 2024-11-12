package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import today.movatech.kollama.impl.KollamaClientImpl
import today.movatech.today.movatech.kollama.impl.json
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateRequest_Options_Test : KollamaTest() {

    @Test
    fun test_generate_response_non_stream() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/generate")
                require(request.method.value == "POST")
                require(request.body is TextContent)

                val generateRequest = json.decodeFromString<GenerateRequest>((request.body as TextContent).text)
                val options = generateRequest.options ?: throw IllegalArgumentException("Options is not set")
                require(options.temperature == 0.1.toFloat())

                respond(
                    content = ByteReadChannel(readResponse("data/generate_with_options.json")),
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
                options = Options(temperature = 0.1F)
            )
            assertEquals(
                "generate_with_options",
                kollamaClient.generate(generateRequest).response
            )
        }
    }
}