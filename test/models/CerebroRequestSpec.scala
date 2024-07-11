package models

import controllers.auth.AuthRequest
import org.specs2.Specification
import org.mockito.Mockito._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Headers

object CerebroRequestSpec extends Specification {

  def is =
    s2"""
    CerebroRequest should
        should forward whitelisted headers          $forwardHeaders

      """

  private def forwardHeaders = {
    val user = None
    val whitelistHeaders = Seq("a-header")
    val body = Json.obj("host" -> "host1")
    val host
    = Host("host1", None, whitelistHeaders)

    val request = mock(classOf[AuthRequest[JsValue]])
    when(request.body).thenReturn(body)
    when(request.headers).thenReturn(Headers("a-header" -> "content"))
    when(request.user).thenReturn(user)

    val hosts = mock(classOf[Hosts])
    when(hosts.getHost("host1")).thenReturn(Some(host))

    CerebroRequest(request, hosts) === CerebroRequest(ElasticServer(host, Seq("a-header" -> "content")), body, user)
  }

}
