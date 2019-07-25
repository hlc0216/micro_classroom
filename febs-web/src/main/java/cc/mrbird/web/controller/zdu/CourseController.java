package cc.mrbird.web.controller.zdu;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.utils.FileUtils;
import cc.mrbird.gen.domain.Course;
import cc.mrbird.web.controller.base.BaseController;
import cc.mrbird.web.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
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
 * @Date: 2018/12/18 17:31
 * @Description:
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {


    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    CourseService courseService;
    
    @RequestMapping("/index")
    public String index() {
        return "zdu/course/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> courseList(QueryRequest request,Course course) {
        return super.selectByPageNumSize(request, () -> this.courseService.findAllCourse(course));
    }

    @RequestMapping("/getCourse")
    @ResponseBody
    public ResponseBo getcourse(Long id) {
        try {
            Course course =this.courseService.findById(id);
            return ResponseBo.ok(course);
        } catch (Exception e) {
            log.error("获取课程信息失败", e);
            return ResponseBo.error("获取课程信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseBo addcourse(Course course) {
        try {
            this.courseService.addCourse(course);
            return ResponseBo.ok("新增课程成功！");
        } catch (Exception e) {
            log.error("新增课程失败", e);
            return ResponseBo.error("新增课程失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseBo deletecourses(String ids) {
        try {
            this.courseService.deleteCourse(ids);
            return ResponseBo.ok("删除课程成功！");
        } catch (Exception e) {
            log.error("删除课程失败", e);
            return ResponseBo.error("删除课程失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseBo updatecourse(Course course) {
        try {
            this.courseService.updateCourse(course);
            return ResponseBo.ok("修改课程成功！");
        } catch (Exception e) {
            log.error("修改课程失败", e);
            return ResponseBo.error("修改课程失败，请联系网站管理员！");
        }
    }


    @RequestMapping("/excel")
    @ResponseBody
    public ResponseBo courseExcel(Course course) {
        try {
            List<Course> list = this.courseService.findAllCourse(course);
            return FileUtils.createExcelByPOIKit("课程表", list, Course.class);
        } catch (Exception e) {
            log.error("导出课程信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/csv")
    @ResponseBody
    public ResponseBo courseCsv(Course course) {
        try {
            List<Course> list = this.courseService.findAllCourse(course);
            return FileUtils.createCsv("课程表", list, Course.class);
        } catch (Exception e) {
            log.error("导出课程信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("/checkcourseName")
    @ResponseBody
    public boolean checkcourseName(String courseName, String oldcourseName) {
        if (StringUtils.isNotBlank(oldcourseName) && StringUtils.equalsIgnoreCase(courseName, oldcourseName)) {
            return true;
        }
        Course result = this.courseService.findByName(courseName);
        return result == null;
    }





}
