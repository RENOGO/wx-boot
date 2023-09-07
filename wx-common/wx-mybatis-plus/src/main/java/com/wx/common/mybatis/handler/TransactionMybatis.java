package com.wx.common.mybatis.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.PostConstruct;

/**
 * @Author wuweixin
 * @Date 2023/2/2 19:03
 * @Version 1.0
 */
@Component
public class TransactionMybatis {

    @Autowired(required = false)
    private DataSourceTransactionManager dataSourceTransactionManager;

    private static TransactionMybatis INSTANCE = null;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }


    /**
     * 用spring管理的话调这个方法
     * @param transactionInterface
     * @return 事务是否执行成功
     */
    public boolean getTransaction(TransactionInterface transactionInterface) {
        if (dataSourceTransactionManager == null) {
            System.out.println("缺少初始化DataSourceTransactionManager");
            return false;
        }
        // 获取事务定义
        DefaultTransactionDefinition df = new DefaultTransactionDefinition();
        // 设置事务隔离级别，开启新的数据
        df.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获取事务状态,相当于开启事务
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(df);
        try {
            // 此处需要执行的sql操作
            if (transactionInterface != null) {
                transactionInterface.transaction();
            }
            dataSourceTransactionManager.commit(transaction);
            return true;
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transaction);
            if (transactionInterface != null) {
                transactionInterface.exception(e);
            }
            return false;
        }
    }


    /***
     * 在不用spring管理的类就调这个方法
     * @param transactionInterface
     * @return
     */
    public static boolean  getSTransaction(TransactionInterface transactionInterface) {
        if (INSTANCE == null) {
            return false;
        }
        return INSTANCE.getTransaction(transactionInterface);
    }

}
