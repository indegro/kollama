package today.movatech.kollama

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GenerateResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("response") val response: String,
    @SerialName("done") val done: Boolean,
    @SerialName("done_reason") val doneReason: String? = null,
)

@Serializable
data class Models(
    @SerialName("models") val models: List<Model>
)

@Serializable
data class Model(
    @SerialName("name") val name: String,
    @SerialName("modified_at") val modifiedAt: String,
    @SerialName("size") val size: Long,
    @SerialName("digest") val digest: String,
    @SerialName("details") val details: ModelDetails
)

@Serializable
data class ModelDetails(
    @SerialName("format") val format: String,
    @SerialName("family") val family: String,
    @SerialName("families") val families: List<String>? = emptyList(),
    @SerialName("parameter_size") val parameterSize: String,
    @SerialName("quantization_level") val quantizationLevel: String,
    @SerialName("parent_model") val parentModel: String? = null
)

@Serializable
data class ChatResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("message") val message: Message,
    @SerialName("done_reason") val doneReason: String? = null,
    @SerialName("done") val done: Boolean,
    @SerialName("metrics") val metrics: Metrics? = null
)

@Serializable
data class Metrics(
    @SerialName("total_duration") val totalDuration: String? = null,
    @SerialName("load_duration") val loadDuration: String? = null,
    @SerialName("prompt_eval_count") val promptEvalCount: Int? = null,
    @SerialName("prompt_eval_duration") val promptEvalDuration: String? = null,
    @SerialName("eval_count") val evalCount: Int? = null,
    @SerialName("eval_duration") val evalDuration: String? = null
)

@Serializable
data class ProgressResponse(
    @SerialName("status") val status: String,
    @SerialName("digest") val digest: String? = null,
    @SerialName("total") val total: Long? = null,
    @SerialName("completed") val completed: Long? = null
)

@Serializable
data class ProcessResponse(
    @SerialName("models") val models: List<ProcessModelResponse>
)

@Serializable
data class ProcessModelResponse(
    @SerialName("name") val name: String,
    @SerialName("model") val model: String,
    @SerialName("size") val size: Long,
    @SerialName("digest") val digest: String,
    @SerialName("details") val details: ModelDetails? = null,
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("size_vram") val sizeVRAM: Long
)