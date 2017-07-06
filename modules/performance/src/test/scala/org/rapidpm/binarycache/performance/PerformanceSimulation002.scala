package org.rapidpm.binarycache.performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class PerformanceSimulation002 extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:7081")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.8")
    .doNotTrackHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36 Vivaldi/1.91.867.42")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val uri1 = "http://localhost:7081/rest/cache/default/eyJDTEFTUyI6Im9yZy5yYXBpZHBtLmJpbmFyeWNhY2hlLmFwaS5kZWZhdWx0a2V5LkRlZmF1bHRDYWNoZUtleSIsIkNPTlRFTlQiOnsia2V5IjoiMTIzIn19"

  val scnPut = scenario("PUT")
    .exec(http("putRequest")
      .put("/rest/cache/default/eyJDTEFTUyI6Im9yZy5yYXBpZHBtLmJpbmFyeWNhY2hlLmFwaS5kZWZhdWx0a2V5LkRlZmF1bHRDYWNoZUtleSIsIkNPTlRFTlQiOnsia2V5IjoiMTIzIn19")
      .body(RawFileBody("coffee-cup.jpg"))
      .headers(headers_0))


  val scnGet = scenario("GET")
    .exec(http("getRequest")
      .get("/rest/cache/default/eyJDTEFTUyI6Im9yZy5yYXBpZHBtLmJpbmFyeWNhY2hlLmFwaS5kZWZhdWx0a2V5LkRlZmF1bHRDYWNoZUtleSIsIkNPTlRFTlQiOnsia2V5IjoiMTIzIn19")
      .headers(headers_0))

  setUp(
    scnPut.inject(
      atOnceUsers(50),
      constantUsersPerSec(100) during (10) randomized
    ),
    scnGet.inject(
      atOnceUsers(50),
      rampUsers(100) over (10)
    ))
    .protocols(httpProtocol)
}