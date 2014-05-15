/**
 * 
 */
package com.r.component.messagecenter.context;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author oky
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-messagecenter.xml", })
public class MessageCenterContextTest {

	@Resource(name = "messageCenterContext")
	private MessageCenterContext messageCenterContext;

	@Test
	public void test() {
		messageCenterContext.sendMessage("mc://rtx/notify/chenwei,rain/录单检核/0/你的申请编号为：APP0400005120607的贷款单已通过高审，现已到了合约签署中，最终审批金额为：150000，期限为：12，请备好相关资料（次贷人栾淑芳:身份证明(身份证)、 个人流水(银行：重庆银行 账户：8636 起止时间：2013-11 至2014-04 )、 工作证明(工作收入证明)、 房产证资料(房屋所有权证、17-9)、 抵押按揭合同(20万/120期房贷：抵押合同)、 还款流水(重庆（9246）：20万/120期房贷)、 住址证明(公共事业)、 后补事项(后补：4月房贷还款流水、核实17-9房产证原件) ;主贷人余治云:身份证明(身份证)、 个人流水(银行：中国建设银行 账户：2165 起止时间：2013-12 至2014-05 、银行：中国银行 账户：0745 起止时间：2013-11 至2014-04 )、 公司证照(营业执照、税务登记证、组织机构代码证、银行开户许可证、验资报告、章程)、 对公流水(银行：中国农业银行 账户：3087 起止时间：2013-11 至2014-04 )、 购销合同(物业服务合同、港城静园物管服务合同、港城.凤鸣香山物管服务合同)、 经营场地资料(经营场地租赁合同、水电费单)、 其他文件(行驶证：别克牌)、 后补事项(后补国税登记证、签约时：重点核实主贷人个人及对公流水。) ;）原件于14个自然日内进行签约（过期无效）。");
	}
}
