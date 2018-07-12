
# 分布式事务解决方案之可靠消息最终一致性

## 分布式事务问题介绍

一个操作需要跨多个服务调用才能完成，那么spring本地事务是不起作用的，即使是用的同一个数据库，spring事务也不会起作用

为什么spring本地事务不起作用，看下面例子

	public String createOrder() {

		orderService.createOrder();	// 创建订单
		itemService.decrCount();	// 减库存
		userAccountService.updateAccount();	//用户账户更新

	}

假设上面三个操作都需要调用不同的服务，显然，当第三个操作用户账户更新失败时，前两个操作并不会回滚，因为并不是一个spring域

这便是微服务化分布式事务问题

## 项目工程介绍

### 项目工程结构
mq-transaction 父工程

mq-transaction-common 公共工程

mq-transaction-order 订单

mq-transaction-item-api  
mq-transaction-item 商品

mq-transaction-message-api  
mq-transaction-message 消息服务

### 数据表

mq_item 商品表  
mq_item_user_record 商品购买记录表  
mq_message 可靠消息服务表  
mq_order 订单表  
mq_user 用户表 

### 分布式事务出现场景模拟
两个服务
- 订单服务
- 商品购买记录服务

用户下单场景：
1. 用户下单调用com.order.service.impl.OrderServiceImpl.createOrder()方法
2. 首先生成订单，调用本地代码写入order表数据
3. 调用mq-transaction-item商品服务com.item.service.impl.ItemServiceImpl.recordOrderItem()
，创建商品购买记录
4. 一些必要的操作
5. 完成下单

异常场景：
- 如果步骤2出错，那么spring本地事务产生作用，数据回滚，没问题
- 如果步骤3出错，首先商品服务的事务发挥作用，商品服务数据回滚；然后错误抛到
调用者订单服务里，订单服务本地事务作用，订单服务数据回滚，数据都回滚，也没有问题
- 如果步骤4出错，本地代码在spring事务作用下数据会回滚，但是调用的商品服务
因为已经调用完成，不属于本地spring事务管理，所以，出现问题了

如果步骤4出错，那么系统就会出现数据不统一情况，这是分布式系统不可避免的事务问题

### 解决分布式事务问题方法
针对出现场景不同，解决方法也不一样，这里只探讨可靠消息最终一致性方案

#### 可靠消息最终一致性方案有两种
- 本地消息服务
- 独立消息服务

#### 本地消息服务
未完待续...