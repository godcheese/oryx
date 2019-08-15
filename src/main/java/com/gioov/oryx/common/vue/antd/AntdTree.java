package com.gioov.oryx.common.vue.antd;

import java.io.Serializable;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-05-30
 */
public class AntdTree implements Serializable {
    private static final long serialVersionUID = 6136394266913997501L;
    private Long id;
    private Long parentId;
    private String title;
    private String key;
    private List<AntdTree> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<AntdTree> getChildren() {
        return children;
    }

    public void setChildren(List<AntdTree> children) {
        this.children = children;
    }
}
