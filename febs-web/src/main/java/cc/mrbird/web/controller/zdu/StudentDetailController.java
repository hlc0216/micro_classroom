package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.gen.dao.HomeWordMapper;
import cc.mrbird.gen.dao.MyHomeWordMapper;
import cc.mrbird.gen.domain.GetUserDetails;
import cc.mrbird.gen.domain.HomeWord;
import cc.mrbird.gen.domain.MyHomeWord;
import cc.mrbird.security.domain.FebsUserDetails;
import cc.mrbird.system.domain.Dept;
import cc.mrbird.web.controller.base.BaseController;
import cc.mrbird.web.service.MyHomeWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("studentDetail")
public class StudentDetailController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/index")
    public ModelAndView index(MyHomeWord myHomeWord) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("system/studentDetail/index");
        modelAndView.addObject("userId",myHomeWord.getUserId());
        return modelAndView;
    }
    @RequestMapping("/indexByUser")
    public ModelAndView indexByUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("system/studentDetail/indexByUser");
        FebsUserDetails userDetails =(FebsUserDetails) GetUserDetails.GetUserDetail();
        Long userId = userDetails.getUserId();
        modelAndView.addObject("userId",userId);
        return modelAndView;
    }

    @Autowired
    MyHomeWordMapper myHomeWordMapper;
    @Autowired
    MyHomeWordService myHomeWordService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> getList(QueryRequest request, MyHomeWord myHomeWord) {

        return super.selectByPageNumSize(request,() -> this.myHomeWordService.getList(myHomeWord));
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public ResponseBo getOne(Long id) {
        try {
            MyHomeWord myHomeWord = this.myHomeWordMapper.selectByPrimaryKey(id);
            return ResponseBo.ok(myHomeWord);
        } catch (Exception e) {
            log.error("获取作业信息失败", e);
            return ResponseBo.error("获取课程信息失败，请联系网站管理员！");
        }
    }

}
