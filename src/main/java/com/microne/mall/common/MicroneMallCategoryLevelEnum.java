
package com.microne.mall.common;

/**
 * @author machaojin
 * 
 * @email 1917939763@qq.com
 * 
 * @apiNote 分类级别
 */
public enum MicroneMallCategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "一级分类"),
    LEVEL_TWO(2, "二级分类"),
    LEVEL_THREE(3, "三级分类");

    private int level;

    private String name;

    MicroneMallCategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public static MicroneMallCategoryLevelEnum getMicroneMallOrderStatusEnumByLevel(int level) {
        for (MicroneMallCategoryLevelEnum MicroneMallCategoryLevelEnum : MicroneMallCategoryLevelEnum.values()) {
            if (MicroneMallCategoryLevelEnum.getLevel() == level) {
                return MicroneMallCategoryLevelEnum;
            }
        }
        return DEFAULT;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}