package com.r.boda.uploadservice.upload.rmi;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.kingdee.boda.facade.loan.LoanBillFacade;

public class BodaRmiProxyFactoryBean extends RmiProxyFactoryBean {
    
    @Override
    public void afterPropertiesSet() {
        setServiceUrl("rmi://190.100.100.56:1199/loanBillFacade");
        setRefreshStubOnConnectFailure(true);
        setLookupStubOnStartup(true);
        setServiceInterface(LoanBillFacade.class);
        
        super.afterPropertiesSet();
    }
}