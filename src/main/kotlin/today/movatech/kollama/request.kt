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
    @JsonNames("system") val system: String? = null,
    @JsonNames("template") val template: String? = null,
    @JsonNames("context") val context: List<Int>? = null,
    @JsonNames("stream") val stream: Boolean = false,
    @JsonNames("raw") val raw: Boolean = false,
    @JsonNames("format") val format: String = "json",
//    @JsonNames("keep_alive") val keepAlive: Duration? = null,
//    @JsonNames("images") val images: List<ImageData>? = null,
//    @JsonNames("options") val options: Map<String, Any> = emptyMap()
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