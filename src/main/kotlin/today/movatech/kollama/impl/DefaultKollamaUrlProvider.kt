package today.movatech.kollama.impl

import today.movatech.kollama.KollamaEndpointProvider

class DefaultKollamaUrlProvider(
    private val protocol: String = "http",
    private val host: String = "localhost",
    private val port: Int = 11434
) : KollamaEndpointProvider {

    override fun listModelsEndpoint(): String = "$protocol://$host:$port/api/tags"

    override fun generateResponseEndpoint(): String = "$protocol://$host:$port/api/generate"
}
