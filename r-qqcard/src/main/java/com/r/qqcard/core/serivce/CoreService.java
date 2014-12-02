/**
 * 
 */
package com.r.qqcard.core.serivce;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.r.qqcard.core.WebAction;
import com.r.qqcard.core.support.AbstractService;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * @author rain
 *
 */
@Service("service.core")
public class CoreService extends AbstractService {

    @Resource(name = "component.webaction")
    private WebAction action;

    /** 程序启动,登陆前的初始化工作 */
    @EventAnn(Event.程序启动)
    public void preLogin() {

    }
}
