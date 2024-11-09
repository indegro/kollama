package today.movatech.kollama

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class GenerateResponse(
    val model: String,
    @JsonNames("created_at")
    val createdAt: String,
    val response: String,
    val done: Boolean,
    @JsonNames("done_reason")
    val doneReason: String? = null,
)