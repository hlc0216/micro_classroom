package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.gen.domain.Course;
import cc.mrbird.system.domain.Dept;
import cc.mrbird.system.service.DeptService;
import cc.mrbird.web.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zch
 * @Date: 2018/12/28 11:42
 * @Description:
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController extends BaseController {

    @Autowired
    DeptService deptService;

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/index")
    public String index() {
        return "system/clazz/clazz";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Dept> courseList(QueryRequest request, Dept dept) {
        try {
            return this.deptService.findAllDeptsByclazz(dept);
        } catch (Exception e) {
            log.error("获取学院列表失败", e);
            return new ArrayList<>();
        } }
}
