package com.itheima.dto;

import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    //后面这两条属性暂时没用，这里只需要用第一条属性
    private String categoryName;

    private Integer copies;
}
