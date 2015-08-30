package repositories

import play.api.Logger

import models.AuthenticationKeyModel

class AuthenticationKeyRepository {

  def findByAuthenticationKey(authenticationKey: String): Option[AuthenticationKeyModel] = {
    Logger.info("AuthenticationKeyRepository findByAuthenticationKey")
    // TODO DB access
    if(authenticationKey == "secret") {
      Some(AuthenticationKeyModel(authenticationKey))
    } else {
      None
    }
  }
}
