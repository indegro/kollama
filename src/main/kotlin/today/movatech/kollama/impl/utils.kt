package today.movatech.today.movatech.kollama.impl

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T : Any> String.decodeFromString(): Result<T?> = runCatching {
    json.decodeFromString(this)
}
