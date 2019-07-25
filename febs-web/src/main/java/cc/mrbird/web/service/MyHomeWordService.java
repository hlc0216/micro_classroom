package cc.mrbird.web.service;


import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.gen.dao.HomeWordDeptMapper;
import cc.mrbird.gen.dao.HomeWordMapper;
import cc.mrbird.gen.dao.MyHomeWordMapper;
import cc.mrbird.gen.domain.*;
import cc.mrbird.security.domain.FebsUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/26 16:25
 * @Description:
 */
@Service
public class MyHomeWordService extends BaseService<MyHomeWord> {

    @Autowired
    MyHomeWordMapper myHomeWordMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public MyHomeWord findById(Long id) {
        Example example = new Example(MyHomeWord.class);
        example.createCriteria().andCondition("lower(id)=", id);
        List<MyHomeWord> list = this.selectByExample(example);
        MyHomeWord myHomeWord= list.get(0);

        return myHomeWord;
    }
    @Autowired
    HomeWordMapper homeWordMapper;

    public List<MyHomeWord> getList(MyHomeWord myHomeWord){
        HomeWord homeWord = new HomeWord();
        homeWord.setId(myHomeWord.getHomeWordId());
        List<HomeWord> homeWord1= homeWordMapper.select(homeWord);
        List<MyHomeWord> myHomeWords = myHomeWordMapper.select(myHomeWord);

        List<MyHomeWord> myHomeWordList = new ArrayList<>(myHomeWords.size());
        myHomeWords.forEach(myHomeWord1 -> {
            homeWord1.forEach(homeWord2 -> {
                if (myHomeWord1.getHomeWordId() == homeWord2.getId()) {
                    myHomeWord1.setHomeWordName(homeWord2.getHomeWordName());
                    myHomeWordList.add(myHomeWord1);
                }
            });
        });
        return myHomeWordList;

    }






    @Autowired
    HomeWordDeptMapper homeWordDeptMapper;
    @Autowired
    HomeWordService homeWordService;

    public List<MyHomeWordVo> findAllMyHomeWord(MyHomeWord myHomeWord) {
        try {
            Example example = new Example(MyHomeWord.class);
            List<HomeWordDept>homeWordDeptsList=this.findHomeWordDept();
            List<MyHomeWordVo> MyVoList = new ArrayList<>();
            homeWordDeptsList.forEach(homeWordDept -> {
                MyHomeWordVo myHomeWordVo = new MyHomeWordVo();
                HomeWordVo homeWordvo= homeWordService.findById(Long.valueOf(homeWordDept.getHomeWordId()));
                BeanUtils.copyProperties(homeWordvo,myHomeWordVo);
                MyVoList.add(myHomeWordVo);
            });
            return MyVoList;
        } catch (Exception e) {
            log.error("获取布置作业失败");
            return new ArrayList<>();
        }
    }

    /**
     *
     * @return  返回所有与该角色相关的作业
     */
    public List<HomeWordDept> findHomeWordDept() {
        FebsUserDetails userDetails =(FebsUserDetails) GetUserDetails.GetUserDetail();
        Long deptId=userDetails.getDeptId();
        HomeWordDept homeWordDept=new HomeWordDept();
        homeWordDept.setDeptId(String.valueOf(deptId));
       return homeWordDeptMapper.select(homeWordDept);
    }
    @Transactional
    public void addMyHomeWord(MyHomeWord myHomeWord) {
        FebsUserDetails userDetails =(FebsUserDetails) GetUserDetails.GetUserDetail();
        Long userId = userDetails.getUserId();
        myHomeWord.setUserId(userId);
        this.save(myHomeWord);
    }

    @Transactional
    public void updateMyHomeWord(MyHomeWord myHomeWord) {
        this.updateNotNull(myHomeWord);
    }

}
