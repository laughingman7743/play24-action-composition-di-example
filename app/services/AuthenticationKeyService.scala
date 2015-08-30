package services

import javax.inject.Inject

import play.api.Logger

import models.AuthenticationKeyModel
import repositories.AuthenticationKeyRepository

class AuthenticationKeyService @Inject() (authenticationKeyRepository: AuthenticationKeyRepository) {

  def authorize(authenticationKey: String): Option[AuthenticationKeyModel] = {
    Logger.info("AuthenticationKeyService authorized")
    // TODO Business logic
    authenticationKeyRepository.findByAuthenticationKey(authenticationKey)
  }
}
