package today.movatech.kollama.impl

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import org.slf4j.LoggerFactory
import today.movatech.kollama.*
import today.movatech.today.movatech.kollama.impl.decodeFromString
import today.movatech.today.movatech.kollama.impl.json

class KollamaClientImpl(
    private val ollamaClientConfig: KOllamaClientConfig = KOllamaClientConfig.Builder().build(),
    engine: HttpClientEngine
) : KollamaClient {
    private val logger = LoggerFactory.getLogger(KollamaClient::class.java)

    private val client = HttpClient(engine)

    override suspend fun tags(): Models {
        val endpoint = ollamaClientConfig.kollamaEndpointProvider.listModelsEndpoint()
        logger.info("Requesting list of models from $endpoint")

        return try {
            val response: HttpResponse = client.get(endpoint) {
                contentType(ContentType.Application.Json)
            }
            parseModelsResponse(response)
        } catch (e: Exception) {
            logger.error("Failed to retrieve models from $endpoint", e)
            Models(emptyList())
        }
    }

    override suspend fun generate(generateRequest: GenerateRequest): GenerateResponse {
        val model = generateRequest.model
        val prompt = generateRequest.prompt ?: ""
        val endpoint = ollamaClientConfig.kollamaEndpointProvider.generateResponseEndpoint()

        logger.info("Generating completion from $endpoint with model $model")

        val response: HttpResponse = client.post(endpoint) {
            contentType(ContentType.Application.Json)
            setBody(
                json.encodeToString(
                    CompletionRequest(
                        model, prompt, system = ""
                    )
                )
            )
        }

        return processGenerateResponse(response)
    }


    override fun shutdown() {
        client.close()
        logger.info("HTTP client closed.")
    }

    @OptIn(InternalAPI::class)
    private suspend fun processGenerateResponse(response: HttpResponse): GenerateResponse {
        val channel = response.content

        val generateRawResponse = buildString {
            flow {
                while (!channel.isClosedForRead) {
                    channel.readUTF8Line()?.let { emit(it) }
                }
            }.collect { line -> append(line) }
        }

        return generateRawResponse.decodeFromString<GenerateResponse>().getOrThrow()
            ?: throw EmptyGenerateResponseException()
    }

    @OptIn(InternalAPI::class)
    private suspend fun parseModelsResponse(response: HttpResponse): Models {
        val channel = response.content

        val modelsRawResponse = buildString {
            flow {
                while (!channel.isClosedForRead) {
                    channel.readUTF8Line()?.let { emit(it) }
                }
            }.collect { line -> append(line) }
        }

        return modelsRawResponse.decodeFromString<Models>().getOrThrow() ?: Models(emptyList())
    }
}