package com.jdreyesp.fdm.utils

import com.jdreyesp.fdm.account.Customer

/**
  * Created by jreyes on 29/6/17.
  */


trait Verifications {

  def verifyRecord(customer : Customer) : Boolean = {
    !customer.name.isEmpty && !customer.address.isEmpty
  }
}

object Verifications extends Verifications