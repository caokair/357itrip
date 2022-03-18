package cn.itrip.controller;

import cn.itrip.common.*;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.dao.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.itrip.pojo.ItripUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RequestMapping("api")
@RestController
public class AuthController {
    ObjectMapper o=new ObjectMapper();
    TokenBiz t=new TokenBiz();
    @Autowired
    sendMail sd;
    @Autowired
    ItripUserMapper um;
    @Autowired
    redisUtil ru;
    @Autowired
    SDKTestSendTemplateSMS sms;
    @RequestMapping("api/dologin")
    public Dto login(HttpServletRequest req, @RequestParam("name") String name, @RequestParam("password") String password)
    throws Exception{
        val m=new HashMap();
        m.put("userCode",name);
        m.put("userPassword",password);

        ItripUser user=null;
        try {
            user = (ItripUser) um.getItripUserListByMap(m).get(0);
        } catch (Exception e) {
            return DtoUtil.returnFail("登录失败","1000");
        }
            String token = t.generateToken(req.getHeader("User-Agent"), user);
            Jedis j=ru.getJedis();
            j.set(token, this.o.writeValueAsString(user));
            ItripTokenVO vo=new ItripTokenVO(token,
                    Calendar.getInstance().getTimeInMillis()*3600*2,
                    Calendar.getInstance().getTimeInMillis());
            j.close();
            return DtoUtil.returnDataSuccess(this.o.writeValueAsString(vo));
    }
    @PostMapping("registerbyphone")
    public Dto Register(@RequestBody ItripUserVO vo) throws Exception {
        if (um.insertItripUser(vo)>0){
            String authCode=""+new Random().nextInt(9999);
            sms.SMS(vo.getUserCode(),authCode);
            Jedis j = ru.getJedis();
            j.set(vo.getUserCode(),authCode);
            j.close();
            return DtoUtil.returnSuccess();
        }
        return DtoUtil.returnFail("失败","100421");
    }
    @PutMapping("validatephone")
    public Dto validate(String user ,String code) throws Exception {
        Jedis j = ru.getJedis();
        if (j.get(user).equals(code)){
            j.close();
            um.updActive(user);
            return DtoUtil.returnSuccess();
        }
        return DtoUtil.returnFail("失败","111");
    }
    @PostMapping("doregister")
    public Dto register(@RequestBody ItripUserVO vo) throws Exception {
        if (um.insertItripUser(vo)>0){
            String authCode=""+new Random().nextInt(9999);
            sd.send(vo.getUserCode(),"验证信息","您的验证码是:"+authCode);
            Jedis j = ru.getJedis();
            j.set(vo.getUserCode(),authCode);
            j.close();
            return DtoUtil.returnSuccess();
        }
        return DtoUtil.returnFail("失败","100421");
    }
    @PutMapping("activate")
    public Dto activate(String user ,String code){
        Jedis j = ru.getJedis();
        if (j.get(user).equals(code)){
            j.close();
            um.updActive(user);
            return DtoUtil.returnSuccess();
        }
        return DtoUtil.returnFail("失败","111");
    }
    @GetMapping("ckusr")
    public Dto ck(String name) throws Exception {
        Map m=new HashMap();
        m.put("userCode",name);
        if (um.getItripUserCountByMap(m)>0){
                return DtoUtil.returnFail("已存在","11111111");
        }
        return DtoUtil.returnSuccess();
    }
}
