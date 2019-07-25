package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.domain.Tree;
import cc.mrbird.common.utils.FileUtils;
import cc.mrbird.gen.domain.Point;
import cc.mrbird.gen.domain.PointVo;
import cc.mrbird.web.controller.base.BaseController;
import cc.mrbird.web.service.CourseService;
import cc.mrbird.web.service.PointService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/20 17:17
 * @Description:
 */
@Controller
@RequestMapping("/point")
public class PointController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PointService pointService;
    @Autowired
    CourseService courseService;
    
    @RequestMapping("/index")
    public String index() {
        return "system/point/point";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<PointVo> pointList(Point point) {
        try {
            return this.pointService.findAllPoints(point);
        } catch (Exception e) {
            logger.error("获取课堂题目集合失败", e);
            return new ArrayList<>();
        }
    }


    @RequestMapping("/tree")
    @ResponseBody
    public ResponseBo getPointTree() {
        try {
            Tree<Point> tree = this.pointService.getPointTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            logger.error("获取课题树失败", e);
            return ResponseBo.error("获取课题树失败！");
        }
    }

    @RequestMapping("/getPoint")
    @ResponseBody
    public ResponseBo getPoint(Long pointId) {
        try {
            Point point = this.pointService.findById(pointId);
            return ResponseBo.ok(point);
        } catch (Exception e) {
            logger.error("获取课题信息失败", e);
            return ResponseBo.error("获取信息失败，请联系网站管理员！");
        }
    }


    @RequestMapping("/checkPointName")
    @ResponseBody
    public boolean checkPointName(String pointName,  String oldPointName) {
        if (StringUtils.isNotBlank(oldPointName) && StringUtils.equalsIgnoreCase(pointName, oldPointName)) {
            return true;
        }
        Point result = this.pointService.findByNameAndType(pointName);
        return result == null;
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseBo addPoint(Point point) {
        String name=point.getPointName();
        try {
            this.pointService.addPoint(point);
            return ResponseBo.ok("新增" + name + "成功！");
        } catch (Exception e) {
            logger.error("新增{}失败", name, e);
            return ResponseBo.error("新增" + name + "失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseBo deletePoints(String ids) {
        try {
            this.pointService.deletePoints(ids);
            return ResponseBo.ok("删除成功！");
        } catch (Exception e) {
            logger.error("删除课堂题目失败", e);
            return ResponseBo.error("删除失败，请联系网站管理员！");
        }
    }
    @RequestMapping("/update")
    @ResponseBody
    public ResponseBo updatePoint(Point point) {
        String name=point.getPointName();
        try {
            this.pointService.updatePoint(point);
            return ResponseBo.ok("修改" + name + "成功！");
        } catch (Exception e) {
            logger.error("修改{}失败", name, e);
            return ResponseBo.error("修改" + name + "失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/excel")
    @ResponseBody
    public ResponseBo pointExcel(Point point) {
        try {
            List<PointVo> list = this.pointService.findAllPoints(point);
            return FileUtils.createExcelByPOIKit("课堂题目表", list, PointVo.class);
        } catch (Exception e) {
            logger.error("带出课堂题目列表Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/csv")
    @ResponseBody
    public ResponseBo pointCsv(Point point) {
        try {
            List<PointVo> list = this.pointService.findAllPoints(point);
            return FileUtils.createCsv("课堂题目表", list, PointVo.class);
        } catch (Exception e) {
            logger.error("导出课堂题目列表Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    
    
}
