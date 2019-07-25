package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.utils.FileUtils;
import cc.mrbird.gen.domain.HomeWord;
import cc.mrbird.gen.domain.HomeWordVo;
import cc.mrbird.web.controller.base.BaseController;
import cc.mrbird.web.service.HomeWordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zch
 * @Date: 2018/12/24 16:23
 * @Description:
 */
@Controller
@RequestMapping("/homeWord")
public class HomeWordController extends BaseController{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HomeWordService homeWordService;

    @RequestMapping("/index")
    public String index() {

        return "system/homeWord/homeWord";

    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> homewordList(QueryRequest request, HomeWord homeword) {
        return super.selectByPageNumSize(request, () -> this.homeWordService.findAllHomeWord(homeword));
    }

    @RequestMapping("/getHomeWord")
    @ResponseBody
    public ResponseBo gethomeword(Long id) {
        try {
            HomeWordVo homeword =this.homeWordService.findById(id);
            return ResponseBo.ok(homeword);
        } catch (Exception e) {
            log.error("获取布置作业信息失败", e);
            return ResponseBo.error("获取布置作业信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseBo addhomeword(HomeWordVo homeWordVo) {
        try {
            this.homeWordService.addHomeWord(homeWordVo);
            return ResponseBo.ok("新增布置作业成功！");
        } catch (Exception e) {
            log.error("新增布置作业失败", e);
            return ResponseBo.error("新增布置作业失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseBo deletehomewords(String ids) {
        try {
            this.homeWordService.deleteHomeWord(ids);
            return ResponseBo.ok("删除布置作业成功！");
        } catch (Exception e) {
            log.error("删除布置作业失败", e);
            return ResponseBo.error("删除布置作业失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseBo updatehomeword(HomeWordVo homeWordVo) {
        try {
            this.homeWordService.updateHomeWord(homeWordVo);
            return ResponseBo.ok("修改布置作业成功！");
        } catch (Exception e) {
            log.error("修改布置作业失败", e);
            return ResponseBo.error("修改布置作业失败，请联系网站管理员！");
        }
    }


    @RequestMapping("/excel")
    @ResponseBody
    public ResponseBo homewordExcel(HomeWord homeword) {
        try {
            List<HomeWordVo> list = this.homeWordService.findAllHomeWord(homeword);
            return FileUtils.createExcelByPOIKit("布置作业表", list, HomeWord.class);
        } catch (Exception e) {
            log.error("导出布置作业信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/csv")
    @ResponseBody
    public ResponseBo homewordCsv(HomeWord homeword) {
        try {
            List<HomeWordVo> list = this.homeWordService.findAllHomeWord(homeword);
            return FileUtils.createCsv("布置作业表", list, HomeWord.class);
        } catch (Exception e) {
            log.error("导出布置作业信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/checkhomeWordName")
    @ResponseBody
    public boolean checkhomewordName(String homewordName, String oldhomewordName) {
        if (StringUtils.isNotBlank(oldhomewordName) && StringUtils.equalsIgnoreCase(homewordName, oldhomewordName)) {
            return true;
        }
        HomeWord result = this.homeWordService.findByName(homewordName);
        return result == null;
    }


}
