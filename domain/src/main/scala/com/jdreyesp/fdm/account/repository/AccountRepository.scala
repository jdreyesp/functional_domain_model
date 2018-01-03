package com.jdreyesp.fdm.account.repository

import com.jdreyesp.fdm.account.Account
import com.jdreyesp.fdm.repository.Repository

import scala.util.Try

/**
  * Created by jreyes on 2/1/18.
  */
trait AccountRepository extends Repository[Account, String] {
  override def query(id: String): Try[Option[Account]]
  override def store(entity: Account): Try[Account]
}
