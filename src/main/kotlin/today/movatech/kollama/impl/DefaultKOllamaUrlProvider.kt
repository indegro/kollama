package today.movatech.kollama.impl

import today.movatech.kollama.KOllamaEndpointProvider

class DefaultKOllamaUrlProvider(
    private val protocol: String = "http",
    private val host: String = "localhost",
    private val port: Int = 11434
) : KOllamaEndpointProvider {

    override fun listModelsEndpoint(): String = "$protocol://$host:$port/api/tags"

    override fun generateResponseEndpoint(): String = "$protocol://$host:$port/api/generate"
}
