package com.eddsteel.rando

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.security.SecureRandom

private val rand: SecureRandom = SecureRandom()
private val pairRE = Regex("(?:[0-9]{1,9}(?:,[0-9]{1,9})?)?")

fun main() {
    embeddedServer(Netty, port = 8085) {
        routing {
            get("/{params?}") {                
                val response: String = call.parameters["params"]?.split(";")?.joinToString("\n") { kv ->
                    if (pairRE.matches(kv)) {
                        val parts = kv.split(",").take(2).map { it.toInt() }.sorted()
                        when (parts.size) {
                            2 -> random(parts.first(), parts.last())
                            1 -> random(to = parts.first())
                            else -> random()
                        }
                    } else {
                        random()
                    }
                } ?: random()

                call.respond(response)
            }
        }
    }.start(wait = true)
}

private fun random(from: Int = 1, to: Int = 10): String = (rand.nextInt(to - from + 1) + from).toString()

