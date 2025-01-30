package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.Result;
import com.itheima.dto.DishDto;
import com.itheima.entity.Dish;
import org.springframework.web.bind.annotation.RequestBody;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
}
