/**
 * QQ相关工具
 */
package com.r.core.tool;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.r.core.bean.JsFunction;
import com.r.core.exceptions.StrEncodingExcepton;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpWebUrl;
import com.r.core.httpsocket.context.Response;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.AssertUtil;
import com.r.core.util.RandomUtil;
import com.r.core.util.ResolveUtil;

/**
 * QQ工具
 * 
 * @author rain
 *
 */
public class QQTool {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(QQTool.class,
            "QQ工具");
    
    /** QQ的js md5算法序列 */
    public static final String HEXSTRING = "0123456789ABCDEF";
    
    /**
     * 获取登陆QQ账号时的验证码图片
     * 
     * @param httpSocket
     *            套接字
     * @param appid
     *            QQ网络应用的唯一标志
     * @param username
     *            QQ账号
     * 
     * @return 验证码图片
     */
    public static final Image getLoginWebVerifycodeImage(HttpSocket httpSocket,
            String appid, String username) {
        AssertUtil.isNotNull("套接字不能为null！", httpSocket);
        AssertUtil.isNotBlank("QQ网络应用ID不能为空！", appid);
        AssertUtil.isNotBlank("用户名不能为空！", username);
        
        HttpWebUrl url = new HttpWebUrl("http://captcha.qq.com/getimage");
        url.add("uin", username);
        url.add("aid", appid);
        url.add(getChecksum());
        
        logger.debug("获取登陆QQ账号时的验证码图片 - {}", url.getUrl());
        return httpSocket.send(url).bodyToImage();
    }
    
    /**
     * 获取登陆QQ账号时的辅助计算码<br/>
     * appid：QQ魔法卡片-10000101<br/>
     * appid：。。。 。。。
     * 
     * @param httpSocket
     *            套接字
     * @param appid
     *            QQ网络应用的唯一标志
     * @param username
     *            QQ账号
     * @return 密码校验的辅助计算码<br/>
     *         参数1:是否需要手动输入验证码标识(1:需要,2:不需要)<br/>
     *         参数2:自动生成的验证码<br/>
     *         参数3:辅助校验码<br/>
     * 
     */
    public static final JsFunction getCheckVC(HttpSocket httpSocket,
            String appid, String username) {
        AssertUtil.isNotNull("套接字不能为null！", httpSocket);
        
        HttpWebUrl url = new HttpWebUrl("http://check.ptlogin2.qq.com/check");
        url.add("uin", username);
        url.add("appid", appid);
        url.add("r", getChecksum());
        
        String checkVC = httpSocket.send(url).bodyToString();
        logger.debug("获取登陆QQ账号时的辅助计算码返回值 - {}", checkVC);
        return ResolveUtil.jsfunction(checkVC);
    }
    
    /**
     * 登陆QQ的web账户<br/>
     * 请传入已经获取过验证码图片且保持着cookies的套接字
     * 
     * @param httpSocket
     *            套接字
     * @param appid
     *            QQ网络应用的唯一标志
     * @param username
     *            账号（一般为QQ号码）
     * @param password
     *            密码
     * @param checkVC
     *            辅助校验码
     * @param verifycode
     *            验证码
     * @return 返回登陆情况<br/>
     *         参数1:登陆提示码(-1:未知错误,0:登陆成功,7:提交的参数错误或者缺失)<br/>
     *         参数5:提示信息<br/>
     *         参数6:用户昵称<br/>
     */
    public static final JsFunction loginWeb(HttpSocket httpSocket,
            String appid, String username, String password, String checkVC,
            String verifycode) {
        AssertUtil.isNotNull("套接字不能为null！", httpSocket);
        AssertUtil.isNotBlank("QQ网络应用ID不能为空！", appid);
        AssertUtil.isNotBlank("用户名不能为空！", username);
        AssertUtil.isNotBlank("密码不能为空！", password);
        AssertUtil.isNotBlank("辅助校验码不能为空！", checkVC);
        AssertUtil.isNotBlank("验证码不能为空！", verifycode);
        
        String pwd = getPassword(checkVC, password, verifycode); // 给密码加密
        
        HttpWebUrl url = new HttpWebUrl("http://ptlogin2.qq.com/login");
        url.add("u", username);
        url.add("p", pwd);
        url.add("aid", appid);
        url.add("verifycode", verifycode);
        
        url.add("js_type", "0");
        url.add("js_ver", "10100");
        url.add("g", "1");
        url.add("h", "1");
        url.add("from_ui", "1");
        url.add("dumy", "");
        // url.add("action", "16-42-283101");
        // url.add("login_sig","4z7R5dHR5Bi5sxTjFK1d4JQBN7Q3WYKrifskUYUB7UW4hVk6r0YD90g4321Xc5Fx");
        // url.add("fp", "loginerroralert");
        url.add("u1",
                "http://imgcache.qq.com/qqshow_v3/htdocs/inc/loginto.html?myurl=http%3A//appimg2.qq.com/card/index_v3.html",
                "gbk");
        
        logger.debug("登陆QQ的web账户 - {}", url.getUrl());
        Response response = httpSocket.send(url);
        String message = response.bodyToString();
        logger.debug("登陆QQ的web后的返回值 - {}", message);
        // ResolveBeanOfJsFunction jsfunction = ResolveUtil.jsfunction(message);
        // return Integer.valueOf(jsfunction.getPar(1)).intValue();
        return ResolveUtil.jsfunction(message);
    }
    
    /**
     * QQ网页操作时,计算执行某些动作需要的校验码(一般在登录完成后获取)
     * 
     * @param skey
     *            (此值从cookie中获取)
     * @return 计算执行某些动作需要的校验码
     */
    public static int getGTK(String skey) {
        int hash = 5381;
        
        for (int i = 0, len = skey.length(); i < len; ++i) {
            hash += (hash << 5) + skey.charAt(i);
        }
        return hash & 0x7fffffff;
    }
    
    /** 获取QQ的Web请求时的识别码(此识别码用来防止重复提交) */
    private static final String getChecksum() {
        return "0." + RandomUtil.randomString("0123456789", 16);
    }
    
    /**
     * 获得QQ网页加密后的密码
     * 
     * @param checkVC
     *            密码校验的辅助计算码<br />
     *            http://check.ptlogin2.qq.com/check?uin={14??????7}&appid=
     *            15000101&r= 12165123123 返回的第三个值
     * @param password
     *            QQ密码
     * 
     * @param verifycode
     *            验证码
     * 
     * @return 加密后的密码
     * 
     * @throws UnsupportedEncodingException
     */
    private static String getPassword(String checkVC, String password,
            String verifycode) {
        
        String P = hexchar2bin(md5(password));
        String U = md5(P
                + hexchar2bin(checkVC.replace("\\x", "").toUpperCase()));
        String V = md5(U + verifycode.toUpperCase());
        
        return V;
    }
    
    /**
     * QQ自己的网页密码加密md5,js实现算法
     * 
     * @throws UnsupportedEncodingException
     */
    private static String md5(String originalText) {
        byte buf[];
        try {
            buf = originalText.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new StrEncodingExcepton("ISO-8859-1编码异常", e);
        }
        StringBuffer hexString = new StringBuffer();
        String result = "";
        String digit = "";
        
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);
            
            byte[] digest = algorithm.digest();
            
            for (int i = 0; i < digest.length; i++) {
                digit = Integer.toHexString(0xFF & digest[i]);
                
                if (digit.length() == 1) {
                    digit = "0" + digit;
                }
                
                hexString.append(digit);
            }
            
            result = hexString.toString();
        } catch (Exception ex) {
            result = "";
        }
        
        return result.toUpperCase();
    }
    
    private static String hexchar2bin(String md5str) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                md5str.length() / 2);
        
        for (int i = 0; i < md5str.length(); i = i + 2) {
            baos.write((HEXSTRING.indexOf(md5str.charAt(i)) << 4 | HEXSTRING.indexOf(md5str.charAt(i + 1))));
        }
        
        try {
            return new String(baos.toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new StrEncodingExcepton("ISO-8859-1编码异常", e);
        }
    }
}
