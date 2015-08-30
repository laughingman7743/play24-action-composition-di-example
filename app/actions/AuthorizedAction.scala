package actions

import scala.concurrent.Future

import play.api.Logger
import play.api.mvc._
import play.api.mvc.Results._

import services.AuthenticationKeyService

trait AuthorizedActionComponent {

  val authenticationKeyService: AuthenticationKeyService

  private def unauthorized: Future[Result] = {
    Logger.error("AuthorizedAction unauthorized")
    Future.successful(Unauthorized)
  }

  case class AuthorizedAction[A](action: Action[A]) extends Action[A] {
    lazy val parser: BodyParser[A] = action.parser

    override def apply(request: Request[A]): Future[Result] = {
      Logger.info("AuthorizedAction apply")
      request.headers.get("X-Authorized").map { key =>
        Logger.info("AuthorizedAction check header")
        authenticationKeyService.authorize(key).map{ result =>
          action(request)
        } getOrElse unauthorized
      } getOrElse {
        request.queryString.get("authorized").map { key =>
          Logger.info("AuthorizedAction check query string")
          authenticationKeyService.authorize(key.head).map { result =>
            action(request)
          } getOrElse unauthorized
        } getOrElse unauthorized
      }
    }
  }

  object AuthorizedAction extends ActionBuilder[Request] {
    override def invokeBlock[A](request: Request[A],
                                block: (Request[A]) => Future[Result]): Future[Result] = {
      block(request)
    }

    override def composeAction[A](action: Action[A]) = new AuthorizedAction(action)
  }
}
