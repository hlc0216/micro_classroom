package cc.mrbird.gen.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.*;
import java.math.BigInteger;

public class Point {
    @Id
    @Column(name = "point_id")
    @ExportConfig(value = "id")
    private Long pointId;

    @ExportConfig(value = "课题名称")
    @Column(name = "point_name")
    private String pointName;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @ExportConfig(value = "状态", convert = "s:0=启用,1=禁用,2=未知")
    private Long status;

    private Long level;

    @ExportConfig(value = "备注")
    private String remark;

    /**
     * @return point_id
     */
    public Long getPointId() {
        return pointId;
    }

    /**
     * @param pointId
     */
    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    /**
     * @return point_name
     */
    public String getPointName() {
        return pointName;
    }

    /**
     * @param pointName
     */
    public void setPointName(String pointName) {
        this.pointName = pointName == null ? null : pointName.trim();
    }

    /**
     * @return PARENT_ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Long status) {
        this.status = status;
    }


    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}