package com.sriman.springbootbankaccount.service;

import com.sriman.springbootbankaccount.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountService extends CrudRepository<Account, Long> {
}