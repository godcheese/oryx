package com.gioov.oryx.common.vue.antd;

import java.io.Serializable;
import java.util.Date;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public class ViewMenuEntityAsAntdTable implements Serializable {
    private static final long serialVersionUID = -8336838049183882340L;

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标（icon）
     */
    private String icon;

    /**
     * 请求地址（url）
     */
    private String url;

    /**
     * 菜单分类 id
     */
    private Long viewMenuCategoryId;

    /**
     * 排序
     */
    private Long sort;

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

    private Integer isGranted;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getViewMenuCategoryId() {
        return viewMenuCategoryId;
    }

    public void setViewMenuCategoryId(Long viewMenuCategoryId) {
        this.viewMenuCategoryId = viewMenuCategoryId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    public Integer getIsGranted() {
        return isGranted;
    }

    public void setIsGranted(Integer isGranted) {
        this.isGranted = isGranted;
    }
}
