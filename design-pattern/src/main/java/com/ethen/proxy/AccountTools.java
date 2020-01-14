package com.ethen.proxy;

import com.ethen.proxy.impl.AccountToolsImpl;

/**
 * 账户操作接口
 */
public interface AccountTools {

    // 查看账户
    void queryAccount(String accountNo);

    // 修改账户
    void updateAccount(String accountNo, String updateInfo);

    /**
     * 代理类(增强{@link AccountToolsImpl}实现)
     * 静态代理
     */
    class AccountToolsProxy implements AccountTools {
        private AccountTools accountTools;

        public AccountToolsProxy(AccountTools accountTools) {
            this.accountTools = accountTools;
        }

        @Override
        public void queryAccount(String accountNo) {
            System.out.println("AccountToolsProxy.queryAccount 开启事务。。。");
            accountTools.queryAccount(accountNo);
            System.out.println("AccountToolsProxy.queryAccount 提交事务。。。");
        }

        @Override
        public void updateAccount(String accountNo, String updateInfo) {
            System.out.println("AccountToolsProxy.updateAccount 开启事务。。。");
            accountTools.updateAccount(accountNo, updateInfo);
            System.out.println("AccountToolsProxy.updateAccount 提交事务。。。");
        }
    }
}
