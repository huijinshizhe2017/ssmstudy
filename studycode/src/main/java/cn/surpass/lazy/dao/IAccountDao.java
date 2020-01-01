package cn.surpass.lazy.dao;

import cn.surpass.domain.Account;
import cn.surpass.domain.AccountUser;

import java.util.List;

public interface IAccountDao {

    List<Account> findAll();

    List<AccountUser> findAllAccount();

    List<Account> findAccountById(int uid);


}
