package com.gioov.oryx.common.vue.antd;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public class DepartmentEntityAsAntdTable implements Serializable {
    private static final long serialVersionUID = -248004506531679645L;

    /**
     * id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 角色值
     */
    private Long parentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    private List<DepartmentEntityAsAntdTable> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public List<DepartmentEntityAsAntdTable> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentEntityAsAntdTable> children) {
        this.children = children;
    }
}
