package today.movatech.kollama.models

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import today.movatech.kollama.KollamaClientImpl
import today.movatech.kollama.KollamaTest
import today.movatech.kollama.ProgressResponse
import today.movatech.kollama.PullRequest
import kotlin.test.Test
import kotlin.test.assertEquals

class KollamaListRunningModels : KollamaTest() {

    @Test
    fun test_list_running_models() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                require(request.url.encodedPath == "/api/ps")
                require(request.method.value == "GET")

                respond(
                    content = ByteReadChannel(readResponse("data/ps.json")),
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

            val runningModelsResponse = kollamaClient.ps()
            assertEquals(1, runningModelsResponse.models.size)
        }
    }
}