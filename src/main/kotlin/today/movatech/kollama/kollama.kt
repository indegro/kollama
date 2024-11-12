package today.movatech.kollama

import today.movatech.kollama.impl.DefaultKollamaUrlProvider

interface KollamaEndpointProvider {
    fun listModelsEndpoint(): String
    fun generateResponseEndpoint(): String
}

interface KollamaClient {

    suspend fun tags(): Models

    suspend fun generate(generateRequest: GenerateRequest): GenerateResponse

    fun shutdown()
}

class KollamaClientConfig private constructor(
    val kollamaEndpointProvider: KollamaEndpointProvider,
) {
    data class Builder(
        var kollamaEndpointProvider: KollamaEndpointProvider = DefaultKollamaUrlProvider(),
        val reqTimeout: Long = 30_000L,
        val defaultModel: CodeModel? = null,
    ) {
        fun build(): KollamaClientConfig = KollamaClientConfig(kollamaEndpointProvider)
    }
}


