package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaChat : KollamaTest() {

    @Test
    fun test_chat_non_stream() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/chat")
                require(request.method.value == "POST")
                require(request.body is TextContent)

                respond(
                    content = ByteReadChannel(readResponse("data/chat.json")),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val kollamaClient = KollamaClientImpl(
                engine = mockEngine
            )

            val chatRequest = ChatRequest(
                model = CODE_GEMMA_2B.modelName,
                messages = listOf(
                    Message("user", content = "why is the sky blue?")
                ),
                stream = false
            )
            assertEquals(
                "Hello! How are you today?",
                kollamaClient.chat(chatRequest).message.content
            )
        }
    }
}