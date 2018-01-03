package com.jdreyesp.fdm.account

import java.util.{Calendar, Date}

import com.jdreyesp.fdm.account.Amount.Amount
import com.jdreyesp.fdm.account.repository.AccountRepository
import com.jdreyesp.fdm.utils.Verifications

import scala.util.{Failure, Success, Try}

/**
  * Created by jreyes on 28/6/17.
  *
  *
  * This trait conforms to the 'building abstractions through incremental composition' approach,
  *  a technique used as a norm in functional programming. See AccountService object to see an example
  *
  */

sealed trait AccountService {

  def today = Calendar.getInstance.getTime

  def openCheckingAccount(customer: Customer, effectiveDate: Date) : AccountRepository => Try[Account]
  def debit(a: Account, amount: Amount) : AccountRepository => Try[Account]
  def credit(a : Account, amount: Amount) : AccountRepository => Try[Account]
  def lookupAccount(id: String) : AccountRepository => Try[Account]
}

object AccountService extends AccountService {

  def verifyCustomer(customer : Customer) : Option[Customer] = {
    if(Verifications.verifyRecord(customer)) Some(customer)
    else None
  }

  override def debit(a: Account, amount: Amount): AccountRepository => Try[Account] =
    (_ : AccountRepository) => {
    if(a.balance.amount < amount) {
      Failure(new Exception("Insufficient balance in account"))
    } else
      Success(a.copy(balance = Balance(a.balance.amount - amount)))
  }

  override def credit(a: Account, amount: Amount): AccountRepository => Try[Account] = {
    (_ : AccountRepository) => Success(a.copy(balance = Balance(a.balance.amount + amount)))
  }

  /**
    * Uses of AccountRepository in this method
    * @param id
    * @return
    */
  override def lookupAccount(id : String): AccountRepository => Try[Account] = {
    accountRepository : AccountRepository => accountRepository.query(id).flatMap {
      case Some(account) => Success(account)
      case _ => Failure(new Exception(s"Account with id $id not found"))
    }
  }

  //TODO: COMPLETE
  /**
    * Extending Function1 as a monad, supporting flatmap with functions,
    *  you can use composition to create complex operations, using currying.
    *
    *  The first thing that you have to do is extend Function1 to support flatmap with functions. This is performed
    *  //TODO: COMPLETE
    *
    *  From your client you can call this curried method like this:
    *
    *  complexOperation(Account(...))(AccountRepositoryOracleDB)
    *
    * @param a
    * @return
    */
  def complexOperation(a : Account) : AccountRepository => Try[Account] = {
    for {
      _ <- debit(a, 30)
      _ <- debit(a, 30)
      accountFn <- credit(a, 60)
    } yield accountFn

  }

  //This entity creation method could be in an AccountFactory
  override def openCheckingAccount(customer: Customer, effectiveDate: Date): AccountRepository => Try[Account] = {
    //Account opening logic
    (_ : AccountRepository) => Success(Account("1", "newAccount", today, customers=Seq(customer)))
  }
}
