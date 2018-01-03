package com.jdreyesp.fdm.account

import java.util.Date

import com.jdreyesp.fdm.account.Amount.Amount

/**
  * Created by jreyes on 2/1/18.
  */
package object Amount {type Amount = BigDecimal}

case class Balance(amount : Amount = 0)
case class Account(no: String, name: String, dateOfOpening: Date, balance: Balance = Balance(), customers: Seq[Customer] = Seq())
case class Customer(name: String, address: String)
