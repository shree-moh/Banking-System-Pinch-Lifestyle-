package com.sriman.springbootbankaccount.service;

import com.sriman.springbootbankaccount.domain.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TransactionsService extends CrudRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByDateBetweenAndType(Date StartOfDay, Date endOfDay, int type);

}
