package cn.itrip.controller;


import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.pojo.ItripLabelDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/hotel")
@RestController
public class bizController {
    @Autowired
    ItripAreaDicMapper mp;
    @Autowired
    ItripLabelDicMapper id;
    @GetMapping("queryhotcity/{type}")
    public Dto GetByType(@PathVariable Integer type) throws Exception{
            Map m=new HashMap();
            m.put("isChina",type);
        return DtoUtil.returnDataSuccess(mp.getItripAreaDicListByMap(m));
    }
    @GetMapping("queryhotelfeature")
    public Dto GetById()  throws Exception{
        Map m=new HashMap();
        m.put("parentId",6);
       return DtoUtil.returnDataSuccess(id.getItripLabelDicListByMap(m));
    }

}
