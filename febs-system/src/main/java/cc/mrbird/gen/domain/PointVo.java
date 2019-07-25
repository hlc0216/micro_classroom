package cc.mrbird.gen.domain;

import cc.mrbird.common.annotation.ExportConfig;

/**
 * @Auther: zch
 * @Date: 2018/12/21 11:53
 * @Description:
 */
public class PointVo {
    private static final long serialVersionUID = -1714476694755654924L;
    private Long pointId;
    @ExportConfig(value = "课题名称")
    private String pointName;
    private Long parentId;
    @ExportConfig(value = "状态", convert = "s:0=启用,1=禁用,2=未知")
    private Long status;

    private Long level;
    @ExportConfig(value = "备注")
    private String remark;
    @ExportConfig(value = "课程")
    private String course;

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
