package cn.itrip.controller;

import cn.itrip.common.*;
import cn.itrip.dao.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.itrip.pojo.ItripUserLinkUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/userinfo")
@RestController
public class userController {
    @Autowired
    ItripUserLinkUserMapper mp;

    @PostMapping("adduserlinkuser")
    public Dto ins( @RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO){
        System.out.println(itripAddUserLinkUserVO.getLinkPhone());
            if (mp.insertItripLinkUserVO(itripAddUserLinkUserVO)>0){
                return DtoUtil.returnSuccess();
            }
            return DtoUtil.returnFail("失败","100421");
    }
    @PostMapping("queryuserlinkuser")
    public Dto sel( @RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO) throws Exception {
        System.out.println(itripSearchUserLinkUserVO.getLinkUserName());
        List<ItripUserLinkUser> x = mp.getItripUserLinkUserByName(itripSearchUserLinkUserVO);
        System.out.println(x);
        return DtoUtil.returnDataSuccess(x);
    }
    @GetMapping("deluserlinkuser")
    public Dto del(Integer ids)throws Exception {
        if (mp.deleteItripUserLinkUserById((long)ids)>0){
            return DtoUtil.returnSuccess();
        }

        return DtoUtil.returnFail("删除失败","100432");
    }
    @PostMapping("modifyuserlinkuser")
    public Dto upd (@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO) throws Exception{
        if (mp.updateItripUserLinkUserOv(itripModifyUserLinkUserVO)>0){
            return DtoUtil.returnSuccess();
        }
        return DtoUtil.returnFail("修改失败","100421 ");
    }

}
