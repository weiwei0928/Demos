package com.ww.demos.recyclerview;

public class ItemData {
    private String content;

    public ItemData(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemData itemData = (ItemData) o;
        return content.equals(itemData.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
