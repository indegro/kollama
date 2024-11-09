package today.movatech.kollama

import today.movatech.kollama.impl.DefaultKOllamaUrlProvider

interface KOllamaEndpointProvider {
    fun listModelsEndpoint(): String
    fun generateResponseEndpoint(): String
}

interface KollamaClient {

    suspend fun tags(): Models

    suspend fun generate(generateRequest: GenerateRequest): GenerateResponse

    fun shutdown()
}

class KOllamaClientConfig private constructor(
    val kollamaEndpointProvider: KOllamaEndpointProvider,
) {
    data class Builder(
        var kollamaEndpointProvider: KOllamaEndpointProvider = DefaultKOllamaUrlProvider(),
        val reqTimeout: Long = 30_000L,
        val defaultModel: CodeModel? = null,
    ) {
        fun build(): KOllamaClientConfig = KOllamaClientConfig(kollamaEndpointProvider)
    }
}


