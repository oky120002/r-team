/**
 * 
 */
package com.r.qqcard.core.serivce;

import org.springframework.stereotype.Service;

import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * @author rain
 *
 */
@Service("service.core")
public class CoreService {

    /** 程序启动,登陆前的初始化工作 */
    @EventAnn(Event.登陆前)
    public void preLogin() {
        System.out.println(1000000);
    }
}
