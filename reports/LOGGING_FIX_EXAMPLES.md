# 日志修复示例代码

## 示例 1: 支付宝接口类修复

### 修复前

```java
package com.alipay;

public class AlipayInterface {
    
    public void someMethod() {
        try {
            // 业务逻辑
            System.out.println("调用成功");
        } catch (Exception e) {
            System.out.println("调用失败");
            e.printStackTrace();
        }
    }
}
```

### 修复后

```java
package com.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hyplat.common.logger.LoggerUtil;

public class AlipayInterface {
    
    private static final Logger logger = LoggerFactory.getLogger(AlipayInterface.class);
    
    public void someMethod() {
        try {
            // 业务逻辑
            logger.info("支付宝接口调用成功");
        } catch (Exception e) {
            logger.error("支付宝接口调用失败", e);
        }
    }
}
```

---

## 示例 2: 带参数的日志

### 修复前

```java
public void processOrder(String orderId, Double amount) {
    System.out.println("处理订单：" + orderId + ", 金额：" + amount);
    
    try {
        // 业务逻辑
    } catch (Exception e) {
        System.out.println("订单处理失败，订单 ID：" + orderId);
        e.printStackTrace();
    }
}
```

### 修复后

```java
public void processOrder(String orderId, Double amount) {
    logger.info("处理订单：{}, 金额：{}", orderId, amount);
    
    try {
        // 业务逻辑
    } catch (Exception e) {
        logger.error("订单处理失败，订单 ID: {}", orderId, e);
    }
}
```

---

## 示例 3: 调试信息

### 修复前

```java
public void debugMethod() {
    System.out.println("DEBUG: 进入方法");
    System.out.println("参数值：" + param);
    // 业务逻辑
    System.out.println("DEBUG: 方法结束");
}
```

### 修复后

```java
public void debugMethod() {
    logger.debug("进入方法");
    logger.debug("参数值：{}", param);
    // 业务逻辑
    logger.debug("方法结束");
}
```

---

## 示例 4: 批量数据操作

### 修复前

```java
public void batchProcess(List<String> dataList) {
    System.out.println("开始批量处理，数据量：" + dataList.size());
    
    for (int i = 0; i < dataList.size(); i++) {
        System.out.println("处理第 " + i + " 条数据");
        // 处理逻辑
    }
    
    System.out.println("批量处理完成");
}
```

### 修复后

```java
public void batchProcess(List<String> dataList) {
    logger.info("开始批量处理，数据量：{}", dataList.size());
    
    for (int i = 0; i < dataList.size(); i++) {
        if (logger.isDebugEnabled()) {
            logger.debug("处理第 {} 条数据", i);
        }
        // 处理逻辑
    }
    
    logger.info("批量处理完成");
}
```

---

## 示例 5: 异常处理最佳实践

### 修复前

```java
public void riskyOperation() {
    try {
        // 风险操作
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

### 修复后

```java
public void riskyOperation() {
    try {
        // 风险操作
    } catch (SQLException e) {
        logger.error("数据库操作失败", e);
        throw new BusinessException("数据库操作失败", e);
    } catch (IOException e) {
        logger.error("IO 操作失败", e);
        throw new BusinessException("IO 操作失败", e);
    } catch (Exception e) {
        logger.error("未知错误", e);
        throw new BusinessException("系统异常", e);
    }
}
```

---

## 快速替换模板 (IDEA Live Templates)

### 模板 1: soutl (日志 info)

```
logger.info("$END$", $PARAM$);
```

### 模板 2: soutle (日志 error)

```
logger.error("$END$", $PARAM$);
```

### 模板 3: soutlw (日志 warn)

```
logger.warn("$END$", $PARAM$);
```

### 模板 4: soutld (日志 debug)

```
logger.debug("$END$", $PARAM$);
```

### 模板 5: logger 声明

```
private static final Logger logger = LoggerFactory.getLogger($CLASS$.class);$END$
```

---

## 检查清单

修复完成后，请确认：

- [ ] 已添加 Logger 导入语句
- [ ] 已声明 logger 实例
- [ ] 所有 System.out.println 已替换
- [ ] 所有 printStackTrace 已替换
- [ ] 日志级别选择合理
- [ ] 参数化日志格式正确
- [ ] 编译无错误
- [ ] 日志输出正常
