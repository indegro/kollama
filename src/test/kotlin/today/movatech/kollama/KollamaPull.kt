package today.movatech.kollama

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaPull : KollamaTest() {

    @Test
    fun test_create_model() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/pull")
                require(request.method.value == "POST")
                require(request.body is TextContent)

                respond(
                    content = ByteReadChannel(readResponse("data/pull.jsonl")),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val kollamaClient = KollamaClientImpl(
                engine = mockEngine
            )

            val pullRequest = PullRequest(
                model = "model",
            )

            val pullResponse = mutableListOf<ProgressResponse>()
            kollamaClient.pull(pullRequest).toList(pullResponse)
            assertEquals(5, pullResponse.size)
        }
    }
}