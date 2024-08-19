package boox

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class ApplicationKtTest {

    @Test
    fun `read basic readme file`() {
        mdReader(readFile("/simple_h1.md")) shouldBe Files.readString(Path.of(readFile("/simple_h1.html")))
    }

    private fun readFile(file: String) = {}.javaClass.getResource(file)?.path!!
}