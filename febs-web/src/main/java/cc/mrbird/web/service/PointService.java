package cc.mrbird.web.service;

import cc.mrbird.common.domain.Tree;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.utils.TreeUtils;
import cc.mrbird.gen.dao.CourseMapper;
import cc.mrbird.gen.dao.PointMapper;
import cc.mrbird.gen.domain.Course;
import cc.mrbird.gen.domain.Point;
import cc.mrbird.gen.domain.PointVo;
import com.mchange.lang.IntegerUtils;
import com.mchange.lang.LongUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zch
 * @Date: 2018/12/20 16:01
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PointService extends BaseService<Point> {

    @Autowired
    PointMapper pointMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseMapper courseMapper;

    public List<PointVo> findAllPoints(Point point) {
        try {
            Example example = new Example(Point.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(point.getPointName())) {
                criteria.andLike("pointName", "%" + point.getPointName() + "%");
            }
            if (point.getStatus() != null) {
                criteria.andCondition("status=",point.getStatus());
            }
            example.setOrderByClause("point_id");

            List<Point>li=this.selectByExample(example);
            List<PointVo> list = this.getCourseName(li);

            return list;
        } catch (NumberFormatException e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    public List<PointVo> getCourseName(List<Point>li) {
        List<PointVo> lik = new ArrayList<>();
        for (Point point : li) {
            PointVo pointVo=new PointVo();

            Course course = new Course();
            course.setId(point.getLevel());
            Course course1=courseMapper.selectOne(course);

            BeanUtils.copyProperties(point,pointVo);
            pointVo.setCourse(course1.getCourseName());
            lik.add(pointVo);
        }
        return lik;
    }


    @Transactional
    public void addPoint(Point point) {
        if (point.getParentId() == null) {
            point.setParentId(Long.valueOf(0));
        }
        this.save(point);
    }

    public Point findByNameAndType(String pointName) {
        Example example = new Example(Point.class);
        example.createCriteria().andCondition("lower(point_name)=", pointName.toLowerCase());
        List<Point> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void deletePoints(String pointIds) {
        List<String> list = Arrays.asList(pointIds.split(","));
        this.batchDelete(list, "pointId", Point.class);
    }

    public Tree<Point> getPointTree() {
        List<Tree<Point>> trees = new ArrayList<>();
        Example example = new Example(Point.class);
        List<Point> points = this.selectByExample(example);
        buildTrees(trees, points);
        return TreeUtils.build(trees);
    }

    private void buildTrees(List<Tree<Point>> trees, List<Point> points) {
        points.forEach(point -> {
            Tree<Point> tree = new Tree<>();
            tree.setId(point.getPointId().toString());
            tree.setParentId(point.getParentId().toString());
            tree.setText(point.getPointName());
            trees.add(tree);
        });
    }




    public Point findById(Long pointId) {
        return this.selectByKey(pointId);
    }

    @Transactional
    public void updatePoint(Point point) {
        if (point.getParentId() == null)
            point.setParentId(Long.valueOf(0));
        this.updateNotNull(point);
    }



}
