package com.itheima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.Result;
import com.itheima.dto.DishDto;
import com.itheima.dto.SetmealDto;
import com.itheima.entity.Category;
import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import com.itheima.entity.Setmeal;
import com.itheima.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;



    @PostMapping
    public Result<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return Result.success("套餐添加成功");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return Result.success(dtoPage);
    }

    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam List<Long> ids) {
        log.info("要删除的套餐id为：{}",ids);
        setmealService.removeWithDish(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/list")
    public Result<List<Setmeal>> list(Setmeal setmeal) {
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, 1);
        //排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmealList = setmealService.list(queryWrapper);
        return Result.success(setmealList);
    }

//    @GetMapping("/list")
//    public Result<List<DishDto>> get(Dish dish) {
//        //条件查询器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        //根据传进来的categoryId查询
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        //只查询状态为1的菜品（在售菜品）
//        queryWrapper.eq(Dish::getStatus, 1);
//        //简单排下序，其实也没啥太大作用
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        //获取查询到的结果作为返回值
//        List<Dish> list = dishService.list(queryWrapper);
//        log.info("查询到的菜品信息list:{}",list);
//        //item就是list中的每一条数据，相当于遍历了
//        List<DishDto> dishDtoList = list.stream().map((item) -> {
//            //创建一个dishDto对象
//            DishDto dishDto = new DishDto();
//            //将item的属性全都copy到dishDto里
//            BeanUtils.copyProperties(item, dishDto);
//            //由于dish表中没有categoryName属性，只存了categoryId
//            Long categoryId = item.getCategoryId();
//            //所以我们要根据categoryId查询对应的category
//            Category category = categoryService.getById(categoryId);
//            if (category != null) {
//                //然后取出categoryName，赋值给dishDto
//                dishDto.setCategoryName(category.getName());
//            }
//            //然后获取一下菜品id，根据菜品id去dishFlavor表中查询对应的口味，并赋值给dishDto
//            Long itemId = item.getId();
//            //条件构造器
//            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            //条件就是菜品id
//            lambdaQueryWrapper.eq(itemId != null, DishFlavor::getDishId, itemId);
//            //根据菜品id，查询到菜品口味
//            List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);
//            //赋给dishDto的对应属性
//            dishDto.setFlavors(flavors);
//            //并将dishDto作为结果返回
//            return dishDto;
//            //将所有返回结果收集起来，封装成List
//        }).collect(Collectors.toList());
//        return Result.success(dishDtoList);
//    }
}
