package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.gen.domain.HomeWord;
import cc.mrbird.gen.domain.MyHomeWord;
import cc.mrbird.web.controller.base.BaseController;
import cc.mrbird.web.service.MyHomeWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Auther: zch
 * @Date: 2018/12/26 15:33
 * @Description:
 */
@Controller
@RequestMapping("/myHomeWord")
public class MyHomeWordCotroller extends BaseController{

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyHomeWordService myHomeWordService;

    @RequestMapping("/index")
    public String index() {
        return "system/myHomeWord/myHomeWord";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> homewordList(QueryRequest request, MyHomeWord myHomeword) {
        return super.selectByPageNumSize(request, () -> this.myHomeWordService.findAllMyHomeWord(myHomeword));
    }

    @RequestMapping("/getMyHomeWord")
    @ResponseBody
    public ResponseBo getMyHomeWord(Long id) {
        try {
            MyHomeWord myHomeWord = this.myHomeWordService.findById(id);
            return ResponseBo.ok(myHomeWord);
        } catch (Exception e) {
            log.error("获取作业信息失败", e);
            return ResponseBo.error("获取课程信息失败，请联系网站管理员！");
        }
    }


    @RequestMapping("/add")
    @ResponseBody
    public ResponseBo addhomeword(MyHomeWord myHomeWord) {
        try {
            this.myHomeWordService.addMyHomeWord(myHomeWord);
            return ResponseBo.ok("新增作业成功！");
        } catch (Exception e) {
            log.error("新增作业失败", e);
            return ResponseBo.error("新增作业失败，请联系网站管理员！");
        }
    }
    @RequestMapping("/update")
    @ResponseBody
    public ResponseBo updatehomeword(MyHomeWord myHomeWord) {
        try {
            this.myHomeWordService.updateMyHomeWord(myHomeWord);
            return ResponseBo.ok("修改作业成功！");
        } catch (Exception e) {
            log.error("修改作业失败", e);
            return ResponseBo.error("修改作业失败，请联系网站管理员！");
        }
    }
}
