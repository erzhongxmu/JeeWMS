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
    private String menuImg;//图片
    private String menuText;//名称
    private String	menuSrc;//地址

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

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
