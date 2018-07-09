
# 分布式事务问题出现场景

一个操作需要跨多个服务调用才能完成，那么spring本地事务是不起作用的，即使是用的同一个数据库，spring事务也不会起作用

为什么spring本地事务不起作用，看下面例子

	public String createOrder() {

		orderService.createOrder();	// 创建订单
		itemService.decrCount();	// 减库存
		userAccountService.updateAccount();	//用户账户更新

	}

假设上面三个操作都需要调用不同的服务，显然，当第三个操作用户账户更新失败时，前两个操作并不会回滚，因为并不是一个spring域

这便是微服务化分布式事务问题

### 模拟分布式事务出现场景

订单生成场景是经典的分布式事务出现场景

创建订单

记录购买信息

### 项目工程结构
mq-transaction 父工程

mq-transaction-common 公共工程

mq-transaction-order 订单

mq-transaction-item-api  
mq-transaction-item 商品

mq-transaction-message-api  
mq-transaction-message 消息服务