package cc.mrbird.web.service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.gen.dao.CourseMapper;
import cc.mrbird.gen.domain.Course;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/20 10:33
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseService extends BaseService<Course> {

    @Autowired
    CourseMapper courseMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public Course findById(Long id) {
        Example example = new Example(Course.class);
        example.createCriteria().andCondition("lower(id)=", id);
        List<Course> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Course> findAllCourse(Course course) {
        try {
            Example example = new Example(Course.class);
            if (StringUtils.isNoneBlank(course.getCourseName())) {
                example.createCriteria().andLike("courseName", "%" + course.getCourseName() + "%");
            }
            example.setOrderByClause("id");
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取课程失败");
            return new ArrayList<>();
        }
    }

    public Course findByName(String name) {
        Example example = new Example(Course.class);
        example.createCriteria().andCondition("lower(course_name)=", name);
        List<Course> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void addCourse(Course course) {
        this.save(course);
    }

    @Transactional
    public void deleteCourse(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        this.batchDelete(list, "id", Course.class);

    }

    @Transactional
    public void updateCourse(Course course) {
        this.updateNotNull(course);
    }

}

