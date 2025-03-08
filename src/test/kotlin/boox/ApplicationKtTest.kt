package boox

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertEquals

class ApplicationKtTest {

    @Test
    fun `hello world`() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

    @Test
    fun `return html version of md`() = testApplication {
        application {
            module()
        }
        val response = client.get("/simple_h1.md")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(Files.readString(Path.of(readFile("/simple_h1.html"))), response.bodyAsText())
    }

    private fun readFile(file: String) = {}.javaClass.getResource(file)?.path!!
}