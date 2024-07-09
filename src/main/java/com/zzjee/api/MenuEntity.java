package com.zzjee.api;

/**
 * @Title: Controller
 * @Description: 出货通知
 * @author erzhongxmu
 * @date 2017-08-15 23:18:59
 * @version V1.0
 *
 */

public class MenuEntity {
    /**
     * 图 片
     */
    private String menuImg;
    /**
     * 名称
     */
    private String menuText;
    /**
     * 地址
     */
    private String	menuSrc;

    /**
     * 获取图片
     * @return
     */
    public String getMenuImg() {
        return menuImg;
    }

    /**
     * 设置图片
     * @param menuImg
     */
    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    /**
     * 获取菜单文本
     * @return
     */
    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuSrc() {
        return menuSrc;
    }

    public void setMenuSrc(String menuSrc) {
        this.menuSrc = menuSrc;
    }
}
