package boox

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    routes(mainRoute(), readFile(), readMdFile())
        .asServer(SunHttp(8080))
        .start()
}

fun mainRoute() = "/" bind Method.GET to {
    Response(OK).body("")
}

fun readFile() = "/{readme}" bind Method.GET to {req ->
    val resource = {}.javaClass.getResource("/${req.path("readme")!!}")
    Response(OK).body(mdReader(resource!!.path))
}

fun readMdFile() = "/md/{readme}" bind Method.GET to {req ->
    val resource = {}.javaClass.getResource("/${req.path("readme")!!}")?.path
    Response(OK).body(Files.readAllLines(Path.of(resource!!)).joinToString())
}


fun mdReader(path: String): String =
    createHTML().html {
        body {
            Files.readAllLines(Path.of(path))
                .map { line ->
                    when {
                        line.startsWith("# ") -> h1 { + line.replace("# ", "") }
                        line.startsWith("## ") -> h2 { + line.replace("## ", "") }
                        line.startsWith("### ") -> h3 { + line.replace("### ", "") }
                        line.startsWith("#### ") -> h4 { + line.replace("#### ", "") }
                        line.startsWith("##### ") -> h5 { + line.replace("##### ", "") }
                        line.startsWith("###### ") -> h6 { + line.replace("###### ", "") }
                    }
                }
        }
    }