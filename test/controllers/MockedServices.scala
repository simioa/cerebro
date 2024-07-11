package controllers

import controllers.auth.AuthenticationModule
import elastic.ElasticClient
import org.specs2.Specification
import org.mockito.Mockito._
import org.specs2.specification.BeforeEach
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.JsValue
import play.api.mvc.Result
import play.api.test.Helpers.{contentAsJson, _}

import scala.concurrent.Future

trait MockedServices extends Specification with BeforeEach {

  val client = mock(classOf[ElasticClient])

  val auth = mock(classOf[AuthenticationModule])
  when(auth.isEnabled).thenReturn(false)

  override def before = {
    org.mockito.Mockito.reset(client)
  }

  val application = new GuiceApplicationBuilder().
    overrides(
      bind[ElasticClient].toInstance(client),
      bind[AuthenticationModule].toInstance(auth)
    ).build()

  def ensure(response: Future[Result], statusCode: Int, body: JsValue) = {
    ((contentAsJson(response) \ "body").as[JsValue] mustEqual body) and
      ((contentAsJson(response) \ "status").as[Int] mustEqual statusCode)
  }

}
