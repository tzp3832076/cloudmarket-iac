// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.AccountType;

/**
 * account mapper test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@InitDatabase(tables = "mkt_account")
public class AccountMapperTest extends BaseMapperTest {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testGetAccountByAccountId() {
        Account account = accountMapper.getByAccountId("abc123");
        Assert.assertNotNull(account);
        Assert.assertEquals(AccountType.BCE, account.getAccountType());
        Assert.assertEquals("VENDOR", account.getRole());
    }

    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setAccountId(IdUtils.generateUUID());
        account.setAccountType(AccountType.BCE);
        account.setRole("AGENT");
        account.setVendorId("");
        int ret = accountMapper.save(account);
        Assert.assertEquals(1, ret);
        Assert.assertTrue(account.getId() > 0);
    }
}
