package boox

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import kotlinx.html.stream.appendHTML
import kotlinx.html.*
import kotlinx.html.dom.*

var buttonPressed = 0

fun main() {
    
    ServerFilters.CatchLensFailure
    .then(
        routes(
           mainRoute(),
           buttonRoute()
        )           
    ).asServer(SunHttp(8080))
    .start()
}

fun mainRoute() = "/" bind Method.GET to { 
    Response(OK).body(bodyHtml{
        div {
            h1{ + "Hello Boox" }
            button() { 
                attributes["hx-post"] = "/button"
                attributes["hx-target"] = "#pressed"
                + "button"
            }
        }
        div {
            id="pressed"
            h2{
                +"Button pressed $buttonPressed ${if(buttonPressed == 1) "time" else "times"}"
            }
        }
    })
}

fun buttonRoute() = "/button" bind Method.POST to {
    buttonPressed++
    Response(OK).body(bodyHtml{
        div {
            id="pressed"
            h2{
                +"Button pressed $buttonPressed ${if(buttonPressed == 1) "time" else "times"}"
            }
        }
    })
}


fun bodyHtml(block: BODY.() -> Unit) = buildString {appendHTML().
    html{ 
        head {
            script(type = ScriptType.textJScript, src= "https://unpkg.com/htmx.org@2.0.1" ) {}
        }
        body(block = block)
    }
}
