# Ubuntu系统配置详细指南

## 1. VMware虚拟机安装Ubuntu

### 1.1 下载Ubuntu镜像

1. 访问：https://ubuntu.com/download/desktop
2. 下载 **Ubuntu 22.04 LTS** 桌面版（或者Server版）
3. 或者直接下载ISO镜像：https://releases.ubuntu.com/22.04/ubuntu-22.04.3-live-server-amd64.iso

### 1.2 创建虚拟机

1. 打开VMware Workstation
2. 点击"创建新虚拟机"
3. 选择"典型(推荐)"
4. 选择"安装程序光盘映像文件(iso)"，浏览选择下载的Ubuntu镜像
5. 设置虚拟机名称：`EnergyPlatform`
6. 分配处理器：**4核**（推荐8核）
7. 分配内存：**8GB**（推荐16GB）
8. 分配磁盘：**60GB**（推荐100GB）
9. 选择网络模式：**NAT**（推荐）或**桥接**
10. 完成创建

### 1.3 安装Ubuntu系统

1. 启动虚拟机
2. 选择语言：中文（简体）或English
3. 选择"安装Ubuntu"
4. 键盘布局：Chinese或English
5. 选择"继续"
6. 选择"清除整个磁盘并安装Ubuntu"（虚拟机专用）
7. 设置时区：上海
8. 设置用户名和密码：
   - 用户名：`admin`
   - 密码：`admin123`
   - 自动登录：勾选
9. 等待安装完成，重启

---

## 2. Ubuntu系统基础配置

### 2.1 换源（提高下载速度）

```bash
# 备份原源
sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak

# 换阿里云源
sudo bash -c 'cat > /etc/apt/sources.list << EOF
deb http://mirrors.aliyun.com/ubuntu/ jammy main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-backports main restricted universe multiverse
EOF'
```

### 2.2 更新系统

```bash
# 更新软件列表
sudo apt update

# 升级所有软件
sudo apt upgrade -y
```

### 2.3 安装基础工具

```bash
# 安装常用工具
sudo apt install -y curl wget vim git net-tools unzip
```

---

## 3. 安装Docker

### 3.1 卸载旧版本（如有）

```bash
sudo apt remove -y docker docker-engine docker.io containerd runc
```

### 3.2 安装依赖

```bash
sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release
```

### 3.3 添加Docker官方GPG密钥

```bash
# 方法一：使用阿里云镜像（推荐）
curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# 方法二：官方源（如果上面的不行）
# curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```

### 3.4 添加Docker仓库

```bash
# 方法一：阿里云（推荐）
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# 方法二：官方源
# echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

### 3.5 安装Docker

```bash
# 更新软件列表
sudo apt update

# 安装Docker
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

### 3.6 验证Docker安装

```bash
# 查看Docker版本
docker --version

# 查看Docker Compose版本
docker compose version

# 测试Docker
sudo docker run hello-world
```

### 3.7 配置Docker开机自启

```bash
sudo systemctl enable docker
sudo systemctl start docker
```

### 3.8 解决权限问题（可选）

```bash
# 将当前用户加入docker组
sudo usermod -aG docker $USER

# 重新登录生效（或者执行下面的命令立即生效）
newgrp docker
```

---

## 4. 安装 Docker Compose

> 说明：推荐使用 Docker 提供的 compose 插件（`docker compose` 命令），
> 无需单独下载 `docker-compose` 二进制，命令也与最新版文档保持一致。

### 4.1 使用 apt 安装 compose 插件（推荐）

```bash
sudo apt update
sudo apt install -y docker-compose-plugin
docker compose version
```

---

## 5. 配置Docker镜像加速

### 5.1 创建配置文件

```bash
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
    "registry-mirrors": [
        "https://docker.mirrors.ustc.edu.cn",
        "https://hub-mirror.c.163.com",
        "https://mirror.baidubce.com"
    ]
}
EOF
```

### 5.2 重启Docker

```bash
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 5.3 验证加速

```bash
docker info | grep -A 10 "Registry Mirrors"
```

---

## 6. 安装Java JDK

### 6.1 安装OpenJDK 17

```bash
sudo apt install -y openjdk-17-jdk
```

### 6.2 验证安装

```bash
java -version
javac -version
```

### 6.3 配置环境变量（通常自动配置）

```bash
# 查看Java路径
which java
ls -la /usr/lib/jvm/
```

---

## 7. 安装Git

### 7.1 安装Git

```bash
sudo apt install -y git
```

### 7.2 配置Git

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 7.3 验证安装

```bash
git --version
```

---

## 8. 配置SSH远程登录（可选）

### 8.1 安装SSH服务

```bash
sudo apt install -y openssh-server
```

### 8.2 启动SSH

```bash
sudo systemctl enable ssh
sudo systemctl start ssh
```

### 8.3 查看IP地址

```bash
ip addr
# 或
ifconfig
```

记住IP地址，后续使用远程终端连接需要用到。

---

## 9. 开放必要端口

### 9.1 使用ufw防火墙

```bash
# 安装ufw
sudo apt install -y ufw

# 开放必要端口
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP
sudo ufw allow 443/tcp   # HTTPS
sudo ufw allow 3306/tcp  # MySQL
sudo ufw allow 6379/tcp  # Redis
sudo ufw allow 8848/tcp  # Nacos
sudo ufw allow 8080/tcp  # 前端/后端端口

# 启用防火墙
sudo ufw enable

# 查看状态
sudo ufw status
```

---

## 10. 一键配置脚本

### 10.1 创建脚本

```bash
mkdir -p ~/scripts
cd ~/scripts
vim init.sh
```

### 10.2 粘贴以下内容

```bash
#!/bin/bash

echo "=========================================="
echo "   Ubuntu系统初始化配置脚本"
echo "=========================================="

# 1. 换源
echo "[1/9] 正在配置软件源..."
sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak
sudo bash -c 'cat > /etc/apt/sources.list << EOF
deb http://mirrors.aliyun.com/ubuntu/ jammy main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-backports main restricted universe multiverse
EOF'

# 2. 更新系统
echo "[2/9] 正在更新系统..."
sudo apt update
sudo apt upgrade -y

# 3. 安装基础工具
echo "[3/9] 正在安装基础工具..."
sudo apt install -y curl wget vim git net-tools unzip

# 4. 安装Docker
echo "[4/9] 正在安装Docker..."
sudo apt remove -y docker docker-engine docker.io containerd runc 2>/dev/null
sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release
curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl enable docker

# 5. 配置Docker镜像加速
echo "[5/9] 正在配置Docker镜像加速..."
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
    "registry-mirrors": [
        "https://docker.mirrors.ustc.edu.cn",
        "https://hub-mirror.c.163.com",
        "https://mirror.baidubce.com"
    ]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker

# 6. 安装JDK
echo "[6/9] 正在安装JDK..."
sudo apt install -y openjdk-17-jdk

# 7. 安装Git
echo "[7/9] 正在安装Git..."
sudo apt install -y git

# 8. 安装openssh-server
echo "[8/9] 正在安装SSH服务..."
sudo apt install -y openssh-server
sudo systemctl enable ssh

echo ""
echo "=========================================="
echo "   配置完成！"
echo "=========================================="
echo ""
echo "验证安装："
echo "  Docker:       $(docker --version)"
echo "  Docker Compose: $(docker compose version)"
echo "  Java:        $(java -version 2>&1 | head -1)"
echo "  Git:         $(git --version)"
echo ""
echo "查看IP地址："
ip addr | grep inet
echo ""
echo "下一步："
echo "  1. 重启系统: sudo reboot"
echo "  2. 部署项目: 上传项目文件并运行 docker-compose up -d"
```

### 10.3 执行脚本

```bash
# 添加执行权限
chmod +x init.sh

# 执行脚本（需要输入密码）
./init.sh
```

---

## 11. 验证所有服务

### 11.1 重启系统

```bash
sudo reboot
```

### 11.2 验证命令

```bash
# 验证Docker
docker --version

# 验证Docker Compose
docker compose version

# 验证Java
java -version

# 验证Git
git --version

# 验证Docker运行
sudo docker run hello-world
```

---

## 12. 常见问题

### 12.1 Docker启动失败

```bash
# 查看错误日志
sudo systemctl status docker
journalctl -xe

# 重试启动
sudo systemctl restart docker
```

### 12.2 镜像下载慢

确保已经配置了镜像加速（步骤5）

### 12.3 权限问题

```bash
# 添加用户到docker组
sudo usermod -aG docker $USER
# 重新登录
```

---

## 13. 下一步

完成以上配置后：

1. 上传项目文件到Ubuntu
2. 解压并进入项目目录
3. 运行 `docker-compose up -d` 启动所有服务
4. 访问各服务验证

---

版本：1.0
更新日期：2026-02
