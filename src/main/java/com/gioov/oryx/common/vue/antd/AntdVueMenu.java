package com.gioov.oryx.common.vue.antd;

import java.io.Serializable;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-04-02
 */
public class AntdVueMenu implements Serializable {
    private static final long serialVersionUID = 2519102551901882649L;

    private Long id;
    private String name;
    private String url;
    private String icon;
    private Long parentId;
    private Boolean isCategory;
    private List<AntdVueMenu> children;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(Boolean category) {
        isCategory = category;
    }

    public List<AntdVueMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AntdVueMenu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "VueMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId=" + parentId +
                ", isCategory=" + isCategory +
                ", children=" + children +
                '}';
    }
}
