package com.jdreyesp.fdm.account

import java.util.{Calendar, Date}

import com.jdreyesp.fdm.account.Amount.Amount
import com.jdreyesp.fdm.utils.Verifications

import scala.util.{Failure, Success, Try}

/**
  * Created by jreyes on 28/6/17.
  */

package object Amount {type Amount = BigDecimal}

case class Balance(amount : Amount = 0)
case class Account(no: String, name: String, dateOfOpening: Date, balance: Balance = Balance(), customers: Seq[Customer] = Seq())
case class Customer(name: String, address: String)

sealed trait AccountService {

  def today = Calendar.getInstance.getTime

  def verifyCustomer(customer : Customer) : Option[Customer] = {
    if(Verifications.verifyRecord(customer)) Some(customer)
    else None
  }

  def openCheckingAccount(customer: Customer, effectiveDate: Date) : Try[Account] = {
    //Account opening logic
    Success(Account("1", "newAccount", today, customers=Seq(customer)))
  }

  def debit(a: Account, amount: Amount) : Try[Account] = {
    if(a.balance.amount < amount) {
      Failure(new Exception("Insufficient balance in account"))
    } else Success(a.copy(balance = Balance(a.balance.amount - amount)))
  }

  def credit(a : Account, amount: Amount) : Try[Account] = {
    Success(a.copy(balance = Balance(a.balance.amount + amount)))
  }

}

object AccountService extends AccountService
