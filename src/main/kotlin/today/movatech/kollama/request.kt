package today.movatech.kollama

import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateRequest(
    @SerialName("model") val model: String,
    @SerialName("prompt") val prompt: String? = null,
    @SerialName("suffix") val suffix: String? = null,
    @SerialName("format") val format: String? = null,
    @SerialName("options") val options: Options? = null,
    @SerialName("system") val system: String? = null,
    @SerialName("template") val template: String? = null,
    @SerialName("stream") val stream: Boolean? = null,
    @SerialName("raw") val raw: Boolean? = null,
    @SerialName("keep_alive") val keepAlive: Int? = null
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
data class ChatRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<Message>,
    @SerialName("tools") val tools: List<Tool>? = null,
    @SerialName("format") val format: String = "json",
    @SerialName("stream") val stream: Boolean = false,
    @SerialName("keep_alive") val keepAlive: String? = "5m",
    @SerialName("options") val options: Options? = null
)

@Serializable
data class Message(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<ImageData>? = null,
    @SerialName("tool_calls") val toolCalls: List<ToolCall>? = null
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
data class CreateRequest(
    @SerialName("model") val model: String,
    @SerialName("modelfile") val modelfile: String,
    @SerialName("stream") val stream: Boolean? = null,
    @SerialName("quantize") val quantize: String? = null,
)

@Serializable
data class PullRequest(
    @SerialName("model") val model: String,
    @SerialName("insecure") val insecure: Boolean? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("stream") val stream: Boolean? = null,
)

