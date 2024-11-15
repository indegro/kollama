package today.movatech.kollama

import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class GenerateRequest(
    @JsonNames("model") val model: String,
    @JsonNames("prompt") val prompt: String,
    @JsonNames("suffix") val suffix: String? = null,
    @JsonNames("format") val format: String? = null,
    @JsonNames("options") val options: Options? = null,
    @JsonNames("system") val system: String? = null,
    @JsonNames("template") val template: String? = null,
    @JsonNames("stream") val stream: Boolean? = null,
    @JsonNames("raw") val raw: Boolean? = null,
    @JsonNames("keep_alive") val keepAlive: String? = null
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

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class ChatRequest(
    @JsonNames("model") val model: String,
    @JsonNames("messages") val messages: List<Message>,
    @JsonNames("tools") val tools: List<Tool>? = null,
    @JsonNames("format") val format: String = "json",
    @JsonNames("stream") val stream: Boolean = false,
    @JsonNames("keep_alive") val keepAlive: String? = "5m",
    @JsonNames("options") val options: Options? = null
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Message(
    @JsonNames("role") val role: String,
    @JsonNames("content") val content: String,
    @JsonNames("images") val images: List<ImageData>? = null,
    @JsonNames("tool_calls") val toolCalls: List<ToolCall>? = null
)

typealias ImageData = ByteArray

@Serializable
data class ToolCall(
    val function: ToolCallFunction
)

@Serializable
data class ToolCallFunction(
    val name: String,
    val arguments: Map<String, @Contextual Any>
)

@Serializable
data class Tool(
    val type: String,
    val function: ToolFunction
)

@Serializable
data class ToolFunction(
    val name: String,
    val description: String,
    val parameters: Parameters
)

@Serializable
data class Parameters(
    val type: String,
    val required: List<String>,
    val properties: Map<String, Property>
)

@Serializable
data class Property(
    val type: String,
    val description: String,
    val enum: List<String>? = null
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class CreateRequest(
    @JsonNames("model") val model: String,
    @JsonNames("modelfile") val modelfile: String,
    @JsonNames("stream") val stream: Boolean? = null,
    @JsonNames("quantize") val quantize: String? = null,
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class PullRequest(
    @JsonNames("model") val model: String,
    @JsonNames("insecure") val insecure: Boolean? = null,
    @JsonNames("username") val username: String? = null,
    @JsonNames("password") val password: String? = null,
    @JsonNames("stream") val stream: Boolean? = null,
)

