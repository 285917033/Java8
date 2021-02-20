package com.zgh.lb;

/**
 * <Description> <br>
 *
 * @author 朱光和
 * @version 1.0<br>
 * @createDate 2021/02/19 9:56 <br>
 */
public class Apple {

    private String color;

    private String name;

    private String from;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Apple(String color) {
        this.color = color;
    }

    public Apple() {
       super();
    }

    public Apple(String color, String name, String from) {
        this.color = color;
        this.name = name;
        this.from = from;
    }
}
