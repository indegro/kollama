package today.movatech.kollama

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T : Any> String.decodeFromString(): Result<T> = runCatching {
    json.decodeFromString(this)
}

inline fun <reified T : Any> T.encodeToString() : String = json.encodeToString(this)
