package com.lei.smart.config;//package com.ucan.framework.dataSource;
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//
//import java.util.Stack;
//
//@Aspect
//@Configuration
//public class TransactionAop {
//    final Logger logger = LogManager.getLogger(TransactionAop.class);
//
//
//    @Pointcut("@annotation(com.ucan.framework.dataSource.CustomTransaction)")
//    public void CustomTransaction() {
//    }
//
//
//    @Pointcut("@annotation(com.ucan.framework.dataSource.CustomTransaction)")
//    public void excudeController() {
//    }
//
//
//    @Around(value = "CustomTransaction()&&excudeController()&&@annotation(annotation)")
//    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint, CustomTransaction annotation) throws Throwable {
//        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<DataSourceTransactionManager>();
//        Stack<TransactionStatus> transactionStatuStack = new Stack<TransactionStatus>();
//
//        try {
//
//            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, annotation)) {
//                return null;
//            }
//
//            Object ret = thisJoinPoint.proceed();
//
//            commit(dataSourceTransactionManagerStack, transactionStatuStack);
//
//            return ret;
//        } catch (Throwable e) {
//
//            rollback(dataSourceTransactionManagerStack, transactionStatuStack);
//
//            logger.error(String.format("MultiTransactionalAspect, method:%s-%s occors error:",
//                    thisJoinPoint.getTarget().getClass().getSimpleName(), thisJoinPoint.getSignature().getName()), e);
//            throw e;
//        }
//    }
//
//    /**
//     * 开启事务处理方法
//     *
//     * @param dataSourceTransactionManagerStack
//     * @param transactionStatuStack
//     * @param multiTransactional
//     * @return
//     */
//    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
//                                    Stack<TransactionStatus> transactionStatuStack, CustomTransaction multiTransactional) {
//
//        String[] transactionMangerNames = multiTransactional.value();
//        if (ArrayUtils.isEmpty(multiTransactional.value())) {
//            return false;
//        }
//
//        for (String beanName : transactionMangerNames) {
//            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager)
//                    SpringContextUtil
//                    .getBean(beanName);
//            TransactionStatus transactionStatus = dataSourceTransactionManager
//                    .getTransaction(new DefaultTransactionDefinition());
//            transactionStatuStack.push(transactionStatus);
//            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
//        }
//        return true;
//    }
//
//    /**
//     * 提交处理方法
//     *
//     * @param dataSourceTransactionManagerStack
//     * @param transactionStatuStack
//     */
//    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
//                        Stack<TransactionStatus> transactionStatuStack) {
//        while (!dataSourceTransactionManagerStack.isEmpty()) {
//            dataSourceTransactionManagerStack.pop().commit(transactionStatuStack.pop());
//        }
//    }
//
//    /**
//     * 回滚处理方法
//     *
//     * @param dataSourceTransactionManagerStack
//     * @param transactionStatuStack
//     */
//    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
//                          Stack<TransactionStatus> transactionStatuStack) {
//        while (!dataSourceTransactionManagerStack.isEmpty()) {
//            dataSourceTransactionManagerStack.pop().rollback(transactionStatuStack.pop());
//        }
//    }
//
//}
