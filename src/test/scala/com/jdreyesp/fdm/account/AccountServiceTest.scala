package com.jdreyesp.fdm.account

import java.util.Calendar

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import com.jdreyesp.fdm.account.AccountService._

import scala.util.{Success, Try}

/**
  * Created by jreyes on 28/6/17.
  */
class AccountServiceTest extends FlatSpec with GivenWhenThen with Matchers {

  def today = Calendar.getInstance().getTime()

  "An account" should "execute a debit operation" in {

    Given("An account with a Balance")
    val account : Account = Account("account 1", "John Smith", today, Balance(100))

    When("A debit operation is executed in that account")
    val newAccount : Try[Account] = debit(account, 100)

    Then("A new account is returned with the new Balance")
    newAccount.isSuccess should be (true)
    newAccount.map(acc => acc.balance.amount) should be (Success(0))

  }

  it should "execute a wrong debit operation" in {

    Given("An account with a Balance")
    val account : Account = Account("account 1", "John Smith", today)
    account.balance == Balance(100)

    When("A debit operation is executed in that account with an ammount that exceeds the account balance")
    Then("An error is thrown")
    debit(account, 200).map(account => fail("Should not success as amount retrieved is more than account balance"))
      .getOrElse(succeed)

  }

  it should "execute a credit operation" in {

    Given("An account with a Balance")
    val account : Account = Account("account 1", "John Smith", today, Balance(100))

    When("A debit operation is executed in that account")
    Then("A new account is returned with the new Balance")
    credit(account, 100).map(account => account.balance.amount) should be (Success(200))

  }

  it should "open a new account for a valid customer" in {
    Given("A customer")
    val customer : Customer = Customer("John Smith", "Smith st.")

    When("Opening a new checking account")
    Then("A new account is returned")
    verifyCustomer(customer).map(customer => {
      openCheckingAccount(customer, today).map(account => account.customers)
    } should be (Success(Seq(customer))))
    .getOrElse(fail("Customer should be valid"))
  }

  it should "open a new account for a not valid customer" in {
    Given("A customer")
    val customer : Customer = Customer("John Smith", "")

    When("Opening a new checking account")
    verifyCustomer(customer).map(customer => {
      fail("Customer should be invalid")
    })
      .getOrElse(succeed)
  }

}
