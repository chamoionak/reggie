package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
