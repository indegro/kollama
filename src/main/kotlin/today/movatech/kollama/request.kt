package today.movatech.kollama

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class CompletionRequest(
    val model: String,
    val prompt: String,
    val system: String
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class GenerateRequest(
    @JsonNames("model") val model: String,
    @JsonNames("prompt") val prompt: String? = null,
    @JsonNames("suffix") val suffix: String? = null,
    @JsonNames("format") val format: String = "json",
    @JsonNames("options") val options: Options? = null,
    @JsonNames("system") val system: String? = null,
    @JsonNames("template") val template: String? = null,
    @JsonNames("stream") val stream: Boolean = false,
    @JsonNames("raw") val raw: Boolean = false,
    @JsonNames("keep_alive") val keepAlive: String? = "5m",
)

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class Models(
    val models: List<Model>
)

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Model(
    val name: String,
    @JsonNames("modified_at")
    val modifiedAt: String,
    val size: Long,
    val digest: String,
    val details: ModelDetail
)

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class ModelDetail(
    val format: String,
    val family: String,
    val families: List<String>? = emptyList(),
    @JsonNames("parameter_size")
    val parameterSize: String,
    @JsonNames("quantization_level")
    val quantizationLevel: String
)

@Serializable
data class Options(
    val numKeep: Int? = null,
    val seed: Int? = null,
    val numPredict: Int? = null,
    val topK: Int? = null,
    val topP: Float? = null,
    val minP: Float? = null,
    val tfsZ: Float? = null,
    val typicalP: Float? = null,
    val repeatLastN: Int? = null,
    val temperature: Float? = null,
    val repeatPenalty: Float? = null,
    val presencePenalty: Float? = null,
    val frequencyPenalty: Float? = null,
    val mirostat: Int? = null,
    val mirostatTau: Float? = null,
    val mirostatEta: Float? = null,
    val penalizeNewline: Boolean? = null,
    val stop: List<String>? = null
)