package today.movatech.kollama

open class KollamaTest {

    fun readResponse(path: String): ByteArray {
        val resource = this::class.java.classLoader.getResource(path)
        requireNotNull(resource) { "Resource $path  not found!" }
        return resource.readBytes()
    }
}
