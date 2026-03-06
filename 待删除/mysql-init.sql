-- Energy Monitor Platform - minimal schema (MySQL 8.0)
-- Charset: utf8mb4

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS energy_monitor
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE energy_monitor;

-- 1) 用户表
CREATE TABLE IF NOT EXISTS `user` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20) UNIQUE,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2) 角色表
CREATE TABLE IF NOT EXISTS role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(200),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3) 权限表
CREATE TABLE IF NOT EXISTS permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  permission_name VARCHAR(50) UNIQUE NOT NULL,
  permission_code VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(200),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4) 用户-角色关系表
CREATE TABLE IF NOT EXISTS user_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES `user`(id),
  CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_user_role_user_id ON user_role(user_id);
CREATE INDEX idx_user_role_role_id ON user_role(role_id);

-- 5) 角色-权限关系表
CREATE TABLE IF NOT EXISTS role_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_role_perm_role FOREIGN KEY (role_id) REFERENCES role(id),
  CONSTRAINT fk_role_perm_perm FOREIGN KEY (permission_id) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_role_permission_role_id ON role_permission(role_id);
CREATE INDEX idx_role_permission_permission_id ON role_permission(permission_id);

-- 6) 设备表
CREATE TABLE IF NOT EXISTS device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_code VARCHAR(50) UNIQUE NOT NULL,
  device_name VARCHAR(100) NOT NULL,
  device_type VARCHAR(50) NOT NULL, -- electricity/gas/water
  location VARCHAR(100) NOT NULL,
  status INT NOT NULL DEFAULT 0, -- 0-offline, 1-online
  user_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_device_user FOREIGN KEY (user_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_device_type ON device(device_type);
CREATE INDEX idx_device_status ON device(status);
CREATE INDEX idx_device_user_id ON device(user_id);

-- 7) 实时数据表
CREATE TABLE IF NOT EXISTS realtime_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_id BIGINT NOT NULL,
  voltage DOUBLE NULL,
  current DOUBLE NULL,
  power DOUBLE NULL,
  energy DOUBLE NULL,
  flow_rate DOUBLE NULL,
  flow_total DOUBLE NULL,
  pressure DOUBLE NULL,
  water_level DOUBLE NULL,
  ph_value DOUBLE NULL,
  turbidity DOUBLE NULL,
  collect_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_realtime_device FOREIGN KEY (device_id) REFERENCES device(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_realtime_device_time ON realtime_data(device_id, collect_time);

-- 8) 历史数据表（小时/天/月聚合）
CREATE TABLE IF NOT EXISTS history_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_id BIGINT NOT NULL,
  data_type VARCHAR(20) NOT NULL, -- hour/day/month
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  voltage_avg DOUBLE NULL,
  current_avg DOUBLE NULL,
  power_avg DOUBLE NULL,
  energy_total DOUBLE NULL,
  flow_rate_avg DOUBLE NULL,
  flow_total DOUBLE NULL,
  pressure_avg DOUBLE NULL,
  water_level_avg DOUBLE NULL,
  ph_value_avg DOUBLE NULL,
  turbidity_avg DOUBLE NULL,
  CONSTRAINT fk_history_device FOREIGN KEY (device_id) REFERENCES device(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_history_device_type_time ON history_data(device_id, data_type, start_time);

-- 9) 阈值设置表
CREATE TABLE IF NOT EXISTS threshold (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_id BIGINT NOT NULL,
  param_name VARCHAR(50) NOT NULL,
  threshold_value DOUBLE NOT NULL,
  alert_type VARCHAR(20) NOT NULL, -- upper/lower
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_threshold_device FOREIGN KEY (device_id) REFERENCES device(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_threshold_device_param ON threshold(device_id, param_name);

-- 10) 告警表
CREATE TABLE IF NOT EXISTS alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_id BIGINT NOT NULL,
  alert_type VARCHAR(50) NOT NULL,
  alert_message VARCHAR(200) NOT NULL,
  alert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status INT NOT NULL DEFAULT 0, -- 0-unhandled, 1-handled
  handle_time DATETIME NULL,
  handler BIGINT NULL,
  CONSTRAINT fk_alert_device FOREIGN KEY (device_id) REFERENCES device(id),
  CONSTRAINT fk_alert_handler FOREIGN KEY (handler) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_alert_device_status ON alert(device_id, status);
CREATE INDEX idx_alert_time ON alert(alert_time);

-- 11) 系统配置表
CREATE TABLE IF NOT EXISTS system_config (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  config_key VARCHAR(50) UNIQUE NOT NULL,
  config_value VARCHAR(255) NOT NULL,
  config_desc VARCHAR(200),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12) 操作日志表
CREATE TABLE IF NOT EXISTS operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  operation_type VARCHAR(50) NOT NULL,
  operation_desc VARCHAR(200) NOT NULL,
  operation_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ip_address VARCHAR(50),
  CONSTRAINT fk_oplog_user FOREIGN KEY (user_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE INDEX idx_oplog_user_time ON operation_log(user_id, operation_time);

-- Seed data (optional demo accounts/roles)
INSERT INTO role (id, role_name, description) VALUES
  (1, '家庭用户', '普通家庭用户，可查看个人监测数据'),
  (2, '管理员', '系统管理员，拥有所有权限'),
  (3, '运维人员', '负责设备管理和系统运维'),
  (4, '分析师', '负责数据分析和报表生成'),
  (5, '审计员', '负责操作日志审计和安全检查')
ON DUPLICATE KEY UPDATE description = VALUES(description);

INSERT INTO permission (permission_name, permission_code, description) VALUES
  ('查看个人数据', 'view_personal_data', '查看个人能源监测数据'),
  ('设置个人阈值', 'set_personal_threshold', '设置个人能源使用阈值'),
  ('管理个人设备', 'manage_personal_device', '管理个人模拟设备'),
  ('查看全局数据', 'view_global_data', '查看所有用户的监测数据'),
  ('管理用户', 'manage_user', '创建、编辑、删除用户账户'),
  ('管理角色', 'manage_role', '管理用户角色和权限'),
  ('管理设备', 'manage_device', '管理所有模拟设备'),
  ('系统配置', 'system_config', '配置系统全局参数'),
  ('数据备份', 'data_backup', '执行系统数据备份'),
  ('查看操作日志', 'view_operation_log', '查看系统操作日志'),
  ('数据分析', 'data_analysis', '进行数据分析和报表生成'),
  ('安全审计', 'security_audit', '执行安全审计和权限检查')
ON DUPLICATE KEY UPDATE description = VALUES(description);

-- demo users (BCrypt hash is placeholder from docs; password: admin123)
INSERT INTO `user` (id, username, password, name, email, phone) VALUES
  (1, 'admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '管理员', 'admin@example.com', '13800138000'),
  (2, 'user', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '家庭用户', 'user@example.com', '13800138001')
ON DUPLICATE KEY UPDATE password = VALUES(password), name = VALUES(name);

INSERT INTO user_role (user_id, role_id) VALUES
  (1, 2),
  (2, 1)
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

-- Default devices (optional)
INSERT INTO device (device_code, device_name, device_type, location, status, user_id) VALUES
  ('ELEC001', '客厅电力监测', 'electricity', '客厅', 1, 2),
  ('GAS001', '厨房燃气监测', 'gas', '厨房', 1, 2),
  ('WATER001', '卫生间用水监测', 'water', '卫生间', 1, 2)
ON DUPLICATE KEY UPDATE device_name = VALUES(device_name), status = VALUES(status);

SET FOREIGN_KEY_CHECKS = 1;

