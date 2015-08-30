package controllers

import javax.inject.Inject

import scala.concurrent.Future

import play.api.libs.json._
import play.api.mvc._

import actions.AuthorizedActionComponent
import services.AuthenticationKeyService

class Application @Inject() (val authenticationKeyService: AuthenticationKeyService)
  extends Controller with AuthorizedActionComponent {

  def index = AuthorizedAction {
    Action.async {
      Future.successful(Ok(Json.obj(
        "status" -> "OK", "statusCode" -> 200, "message" -> "Hello world!"
      )))
    }
  }
}
