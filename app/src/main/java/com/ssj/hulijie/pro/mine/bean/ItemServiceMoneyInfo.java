package com.ssj.hulijie.pro.mine.bean;

/**
 * @author vic_zhang .
 *         on 2018/5/18
 */

public class ItemServiceMoneyInfo {

    /**
     * add : 0.02
     * draw : 0
     * frozen : 0
     * can_draw : 0.02
     */

    //盈利
    private String add;
    //提现
    private String draw;
    //冻结
    private String frozen;
    //可提现
    private String can_draw;

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public String getCan_draw() {
        return can_draw;
    }

    public void setCan_draw(String can_draw) {
        this.can_draw = can_draw;
    }
}
