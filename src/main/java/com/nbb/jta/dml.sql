-- MySQL 8.0+：必须授予 XA_RECOVER_ADMIN 权限

GRANT XA_RECOVER_ADMIN ON *.* TO 'your_username'@'%';
FLUSH PRIVILEGES;




------ 测试 XA 事务正常提交（无需开启事务，同一个connection即可）
-- 步骤1：开始XA事务（xid需全局唯一，格式可自定义）
XA START 'xa_tx_001';
-- 步骤2：执行事务操作（如插入数据）
INSERT INTO xa_test (name) VALUES ('XA事务提交测试');
-- 步骤3：结束事务分支
XA END 'xa_tx_001';
-- 步骤4：准备事务（进入PREPARED状态，等待协调器通知）
XA PREPARE 'xa_tx_001';


-- 步骤5：提交事务（所有资源管理器确认后提交）
XA COMMIT 'xa_tx_001';
-- 验证结果：查询数据是否插入成功
SELECT * FROM xa_test WHERE name = 'XA事务提交测试';



------ 测试 XA 事务正常回滚（无需开启事务，同一个connection即可）
-- 步骤1：开始另一个XA事务（使用新的xid）
XA START 'xa_tx_002';
-- 步骤2：执行事务操作（插入数据）
INSERT INTO xa_test (name) VALUES ('XA事务回滚测试');
-- 步骤3：结束事务分支
XA END 'xa_tx_002';
-- 步骤4：准备事务
XA PREPARE 'xa_tx_002';


-- 步骤5：回滚事务（放弃执行）
XA ROLLBACK 'xa_tx_002';
-- 验证结果：查询数据是否被回滚（应无结果）
SELECT * FROM xa_test WHERE name = 'XA事务回滚测试';
