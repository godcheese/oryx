package com.gioov.oryx.common.vue.antd;

import java.io.Serializable;
import java.util.List;

/**
 * Antd Design Vue TreeSelect 树选择
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-05-01
 */
public class AntdTreeNode implements Serializable {
    private static final long serialVersionUID = 7463926597781806452L;

    private Long id;
    private Long parentId;

    /**
     * 默认根据此属性值进行筛选（其值在整个树范围内唯一）
     */
    private String value;

    /**
     * 树节点显示的内容
     */
    private String label;

    private List<AntdTreeNode> children;

    /**
     * 是否禁用	boolean	false
     */
    private boolean disabled;

    /**
     * 禁掉 checkbox	boolean	false
     */
    private boolean disableCheckbox;

    /**
     * 	是否可选	boolean	true
     */
    private boolean selectable;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<AntdTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<AntdTreeNode> children) {
        this.children = children;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
