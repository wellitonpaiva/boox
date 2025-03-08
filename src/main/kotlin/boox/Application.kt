package boox

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.h4
import kotlinx.html.h5
import kotlinx.html.h6
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/{readme}") {
            val resource = {}.javaClass.getResource("/${call.parameters["readme"]!!}")
            call.respondHtml(HttpStatusCode.OK) {
                parseMdToHtml(resource!!.path)
            }
        }
    }
}

fun HTML.parseMdToHtml(resource: String) {
    body {
        Files.readAllLines(Path.of(resource))
            .map { line ->
                when {
                    line.startsWith("# ") -> h1 { +line.replace("# ", "") }
                    line.startsWith("## ") -> h2 { +line.replace("## ", "") }
                    line.startsWith("### ") -> h3 { +line.replace("### ", "") }
                    line.startsWith("#### ") -> h4 { +line.replace("#### ", "") }
                    line.startsWith("##### ") -> h5 { +line.replace("##### ", "") }
                    line.startsWith("###### ") -> h6 { +line.replace("###### ", "") }
                }
            }
    }
}
