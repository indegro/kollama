package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import today.movatech.kollama.impl.KollamaClientImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaClientTest {

    @Test
    fun test_list_models() {
        runBlocking {
            val mockEngine = MockEngine { request ->
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

    @Test
    fun test_generate_response_non_stream() {
        runBlocking {
            val mockEngine = MockEngine { request ->
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
                stream = false
            )
            assertEquals("The sky is blue because it is the color of the sky.", kollamaClient.generate(generateRequest).response)
        }
    }

    private fun readResponse(path: String): ByteArray {
        val resource = this::class.java.classLoader.getResource(path)
        requireNotNull(resource) { "Resource $path  not found!" }
        return resource.readBytes()
    }
}