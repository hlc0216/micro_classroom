package cc.mrbird.web.service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.gen.dao.HomeWordDeptMapper;
import cc.mrbird.gen.dao.HomeWordMapper;
import cc.mrbird.gen.domain.HomeWord;
import cc.mrbird.gen.domain.HomeWordDept;
import cc.mrbird.gen.domain.HomeWordVo;
import cc.mrbird.system.dao.DeptMapper;
import cc.mrbird.system.domain.Dept;
import cc.mrbird.system.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/24 16:35
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HomeWordService extends BaseService<HomeWord> {

    @Autowired
    HomeWordMapper homeWordMapper;
    @Autowired
    HomeWordDeptMapper homeWordDeptMapper;
    @Autowired
    DeptService deptService;
    @Autowired
    PointService pointService;

    @Autowired
    DeptMapper deptMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public HomeWordVo findById(Long id) {
        Example example = new Example(HomeWord.class);
        example.createCriteria().andCondition("lower(id)=", id);
        List<HomeWord> list = this.selectByExample(example);
        HomeWord homeWord= list.get(0);
        HomeWordVo homeWordVo=new HomeWordVo();
        BeanUtils.copyProperties(homeWord,homeWordVo);
        List<HomeWordDept> HomeWordDeptList = this.findHomeWordDept(homeWordVo);
        homeWordVo.setDeptName(this.findDeptName(HomeWordDeptList));
        homeWordVo.setDeptId(this.findDeptIds(HomeWordDeptList));
        homeWordVo.setPointName(pointService.selectByKey(homeWordVo.getCourseId()).getPointName());
        return homeWordVo;
    }

    public List<HomeWordVo> findAllHomeWord(HomeWord homeWord) {
        try {
            Example example = new Example(HomeWord.class);
            if (StringUtils.isNoneBlank(homeWord.getHomeWordName())) {
                example.createCriteria().andLike("homeWordName","%"+homeWord.getHomeWordName()+"%");
            }
            example.setOrderByClause("id");
            List<HomeWord>homeWords=this.selectByExample(example);
            List<HomeWordVo> homeWordVos = new ArrayList<>(homeWords.size());
            homeWords.forEach(homeWord1 -> {
                HomeWordVo homeWordVo = new HomeWordVo();
                BeanUtils.copyProperties(homeWord1, homeWordVo);
                List<HomeWordDept> list = this.findHomeWordDept(homeWordVo);
                homeWordVo.setDeptName(this.findDeptName(list));
                homeWordVo.setDeptId(this.findDeptIds(list));
                homeWordVo.setPointName(pointService.selectByKey(homeWordVo.getCourseId()).getPointName());
                homeWordVos.add(homeWordVo);

            });
            return homeWordVos;
        } catch (Exception e) {
            log.error("获取布置作业失败");
            return new ArrayList<>();
        }
    }
    public  String findDeptIds(List<HomeWordDept>list){
        StringBuilder deptIds = new StringBuilder();
        for(int i=0;i<list.size();i++) {
            HomeWordDept homeWordDept = list.get(i);
            Dept dept=this.findDept(homeWordDept);
            deptIds=i==list.size()-1?deptIds.append(dept.getDeptId()):deptIds.append(dept.getDeptId()).append(",");
        }
        return deptIds.toString();
    }
    public  String findDeptName(List<HomeWordDept>list){
        StringBuilder deptNames = new StringBuilder();
        for(int i=0;i<list.size();i++) {
            HomeWordDept homeWordDept = list.get(i);
            Dept dept=this.findDept(homeWordDept);
            deptNames=i == list.size() - 1?deptNames.append(dept.getDeptName()): deptNames.append(dept.getDeptName()).append(",");
        }
        return deptNames.toString();
    }

    public Dept findDept(HomeWordDept homeWordDept) {
        Dept dept = new Dept();
        dept.setDeptId(Long.valueOf(homeWordDept.getDeptId()));
       return deptMapper.selectOne(dept);
    }
    public List<HomeWordDept> findHomeWordDept(HomeWordVo homeWordVo) {
        HomeWordDept homeWordDept = new HomeWordDept();
        homeWordDept.setHomeWordId(String.valueOf(homeWordVo.getId()));
        return  homeWordDeptMapper.select(homeWordDept);

    }


    public HomeWord findByName(String name) {
        Example example = new Example(HomeWord.class);
        example.createCriteria().andCondition("lower(home_word_name)=", name);
        List<HomeWord> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void addHomeWord(HomeWordVo homeWordVo) {

        final HomeWord homeWord=new HomeWord();
        BeanUtils.copyProperties(homeWordVo,homeWord);
        this.save(homeWord);
        String ids = homeWordVo.getDeptId();
        Arrays.asList(ids.split(",")).forEach(deptId->{
            HomeWordDept dept = new HomeWordDept();
            dept.setDeptId(deptId);
            dept.setHomeWordId(String.valueOf(homeWord.getId()));
            homeWordDeptMapper.insert(dept);
        });
    }

    @Transactional
    public void deleteHomeWord(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        this.batchDelete(list, "id", HomeWord.class);
        Arrays.asList(ids.split(",")).forEach(id->{
            Example example = new Example(HomeWord.class);
            example.createCriteria().andCondition("id=",id);
            homeWordMapper.deleteByExample(example);
            Example example1 = new Example(HomeWordDept.class);
            example1.createCriteria().andCondition("home_word_id=",id);
            homeWordDeptMapper.deleteByExample(example1);
        });

    }

    @Transactional
    public void updateHomeWord(HomeWordVo homeWordVo) {
        final HomeWord homeWord=new HomeWord();
        BeanUtils.copyProperties(homeWordVo,homeWord);
        this.updateNotNull(homeWord);

        HomeWordDept homeWordDept = new HomeWordDept();
        homeWordDept.setHomeWordId(String.valueOf(homeWord.getId()));
        homeWordDeptMapper.delete(homeWordDept);

        String ids = homeWordVo.getDeptId();
        Arrays.asList(ids.split(",")).forEach(deptId->{
            HomeWordDept dept = new HomeWordDept();
            dept.setDeptId(deptId);
            dept.setHomeWordId(String.valueOf(homeWord.getId()));
            homeWordDeptMapper.insert(dept);
        });
    }


}
