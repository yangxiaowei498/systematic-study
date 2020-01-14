package com.ethen.proxy.impl;

import com.ethen.proxy.AccountTools;

/**
 * 账户工具委托类(包含业务逻辑)
 */
public class AccountToolsImpl implements AccountTools {
    @Override
    public void queryAccount(String accountNo) {
        System.err.println(String.format("queryAccount:%s", accountNo));
    }

    @Override
    public void updateAccount(String accountNo, String updateInfo) {
        System.err.println(String.format("updateAccount:%s->updateInfo:%s", accountNo, updateInfo));
    }
}
