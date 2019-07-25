package cc.mrbird.web.service;

import cc.mrbird.gen.dao.TestTMapper;
import cc.mrbird.gen.domain.TestT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/12 15:43
 * @Description:
 */
@Service
public class testService {
    @Autowired
    TestTMapper testTMapper;

    public List<TestT> findTest(){
        return testTMapper.selectAll();
    }
}
