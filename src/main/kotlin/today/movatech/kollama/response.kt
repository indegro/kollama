package today.movatech.kollama

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class GenerateResponse(
    @JsonNames("model") val model: String,
    @JsonNames("created_at") val createdAt: String,
    @JsonNames("response") val response: String,
    @JsonNames("done") val done: Boolean,
    @JsonNames("done_reason") val doneReason: String? = null,
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Models(
    @JsonNames("models") val models: List<Model>
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Model(
    @JsonNames("name") val name: String,
    @JsonNames("modified_at") val modifiedAt: String,
    @JsonNames("size") val size: Long,
    @JsonNames("digest") val digest: String,
    @JsonNames("details") val details: ModelDetail
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class ModelDetail(
    @JsonNames("format") val format: String,
    @JsonNames("family") val family: String,
    @JsonNames("families") val families: List<String>? = emptyList(),
    @JsonNames("parameter_size") val parameterSize: String,
    @JsonNames("quantization_level") val quantizationLevel: String
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class ChatResponse(
    @JsonNames("model") val model: String,
    @JsonNames("created_at") val createdAt: String,
    @JsonNames("message") val message: Message,
    @JsonNames("done_reason") val doneReason: String? = null,
    @JsonNames("done") val done: Boolean,
    @JsonNames("metrics") val metrics: Metrics? = null
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Metrics(
    @JsonNames("total_duration") val totalDuration: String? = null,
    @JsonNames("load_duration") val loadDuration: String? = null,
    @JsonNames("prompt_eval_count") val promptEvalCount: Int? = null,
    @JsonNames("prompt_eval_duration") val promptEvalDuration: String? = null,
    @JsonNames("eval_count") val evalCount: Int? = null,
    @JsonNames("eval_duration") val evalDuration: String? = null
)