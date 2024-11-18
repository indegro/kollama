package today.movatech.kollama

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import java.net.URI

interface KollamaClient {
    suspend fun generate(generateRequest: GenerateRequest): GenerateResponse

    suspend fun chat(chatRequest: ChatRequest): ChatResponse

    suspend fun create(createRequest: CreateRequest): Flow<ProgressResponse>

    suspend fun tags(): Models

    suspend fun pull(pullRequest: PullRequest): Flow<ProgressResponse>

    suspend fun ps(): ProcessResponse

    fun shutdown()
}

class KollamaClientConfig private constructor(
    val protocol: String,
    val host: String,
    val port: Int,
) {
    class Builder {
        private var protocol: String = "http"
        private var host: String = "localhost"
        private var port: Int = 11434

        fun protocol(protocol: String) = apply { this.protocol = protocol }
        fun host(host: String) = apply { this.host = host }
        fun port(port: Int) = apply { this.port = port }

        fun endpoint(endpoint: String): Builder {
            val uri = try {
                URI(endpoint)
            } catch (e: Exception) {
                URI("http://localhost:11434")
            }

            protocol = uri.scheme
            host = uri.host
            port = uri.port
            return this
        }

        fun build(): KollamaClientConfig {
            return KollamaClientConfig(protocol, host, port)
        }
    }
}


class KollamaClientImpl(
    kollamaClientConfig: KollamaClientConfig = KollamaClientConfig.Builder().build(),
    engine: HttpClientEngine
) : KollamaClient {
    private val logger = LoggerFactory.getLogger(KollamaClient::class.java)
    private val client = HttpClient(engine)
    private val ollamaBaseEndpoint =
        "${kollamaClientConfig.protocol}://${kollamaClientConfig.host}:${kollamaClientConfig.port}/api"

    override suspend fun tags(): Models {
        val response: HttpResponse = client.get("$ollamaBaseEndpoint/tags") {
            contentType(ContentType.Application.Json)
        }
        return processResponse(response)
    }

    override suspend fun generate(generateRequest: GenerateRequest): GenerateResponse {
        val response = sendRequestWithBody("$ollamaBaseEndpoint/generate", generateRequest)
        return processResponse(response)
    }

    override suspend fun chat(chatRequest: ChatRequest): ChatResponse {
        val response = sendRequestWithBody("$ollamaBaseEndpoint/chat", chatRequest)
        return processResponse(response)
    }

    override suspend fun create(createRequest: CreateRequest): Flow<ProgressResponse> {
        val response = sendRequestWithBody("$ollamaBaseEndpoint/create", createRequest)
        return processResponseFlow(response)
    }

    override suspend fun pull(pullRequest: PullRequest): Flow<ProgressResponse> {
        val response = sendRequestWithBody("$ollamaBaseEndpoint/pull", pullRequest)
        return processResponseFlow(response)
    }

    override suspend fun ps(): ProcessResponse {
        val response: HttpResponse = client.get("$ollamaBaseEndpoint/ps") {
            contentType(ContentType.Application.Json)
        }
        return processResponse(response)
    }

    private suspend inline fun <reified ResponseType : Any> processResponseFlow(response: HttpResponse): Flow<ResponseType> {
        val channel = response.bodyAsChannel()
        return flow {
            while (!channel.isClosedForRead) {
                channel
                    .readUTF8Line()
                    ?.let {
                        it.decodeFromString<ResponseType>()
                            .onSuccess { result ->
                                emit(result)
                            }
                    }
            }
        }
    }

    override fun shutdown() {
        client.close()
        logger.info("HTTP client closed.")
    }

    private suspend inline fun <reified T> sendRequestWithBody(endpoint: String, body: T): HttpResponse =
        client.post(endpoint) {
            contentType(ContentType.Application.Json)

            if (body != null) {
                setBody(body.encodeToString())
            }
        }

    private suspend inline fun <reified T : Any> processResponse(response: HttpResponse): T {
        return response.body<String>()
            .decodeFromString<T>()
            .getOrThrow()
    }
}


