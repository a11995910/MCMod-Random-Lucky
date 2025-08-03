# RandomLucky Mod 打包说明

## 📦 构建输出位置

### 主要文件路径
```
/Users/wangjun/MyProGram/MCMod/randomlucky/
├── build/libs/                    # 构建输出目录 (将生成于此)
│   ├── randomlucky-1.21.1-1.0.0.jar       # 发布版JAR
│   └── randomlucky-1.21.1-1.0.0-dev.jar   # 开发版JAR
├── src/                           # 源代码目录
└── README.md                      # 项目说明
```

## 🔧 构建环境要求

### 必需环境
- **Java 21**: OpenJDK 21 或 Oracle JDK 21
- **Gradle 8.1.1**: 兼容ForgeGradle 6.0.24
- **网络连接**: 首次构建需要下载依赖

### 环境配置命令
```bash
# 1. 安装Java 21 (macOS)
brew install openjdk@21
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
export PATH=$JAVA_HOME/bin:$PATH

# 2. 安装Gradle 8.1.1
wget https://services.gradle.org/distributions/gradle-8.1.1-bin.zip
unzip gradle-8.1.1-bin.zip
export PATH=/path/to/gradle-8.1.1/bin:$PATH

# 3. 验证环境
java -version  # 应显示21.x.x
gradle -version  # 应显示8.1.1
```

## 🚀 构建命令

### 标准构建流程
```bash
cd /Users/wangjun/MyProGram/MCMod/randomlucky

# 1. 初始化项目
./gradlew setup

# 2. 构建项目
./gradlew build

# 3. 查看输出
ls -la build/libs/
```

### 快速构建 (如果环境已配置)
```bash
./gradlew clean build --no-daemon
```

## 📁 完整输出结构

构建成功后，将在以下位置生成文件：

```
build/
├── libs/
│   ├── randomlucky-1.21.1-1.0.0.jar          # 👈 主要发布文件
│   ├── randomlucky-1.21.1-1.0.0-dev.jar      # 开发版本
│   ├── randomlucky-1.21.1-1.0.0-sources.jar  # 源代码JAR
│   └── randomlucky-1.21.1-1.0.0-javadoc.jar  # 文档JAR
├── classes/
├── resources/
├── tmp/
└── reports/
```

## 🎯 安装路径

### Minecraft客户端安装
```bash
# 复制主JAR文件到Minecraft mods目录
cp build/libs/randomlucky-1.21.1-1.0.0.jar ~/.minecraft/mods/

# macOS路径
~/Library/Application Support/minecraft/mods/

# Windows路径
%APPDATA%\.minecraft\mods\

# Linux路径
~/.minecraft/mods/
```

### 服务器安装
```bash
# 复制到服务器mods目录
cp build/libs/randomlucky-1.21.1-1.0.0.jar /path/to/minecraft/server/mods/
```

## 🔧 故障排除

### 常见构建问题

1. **Java版本错误**
   ```bash
   # 问题: 系统Java版本不是21
   # 解决: 设置JAVA_HOME
   export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
   ```

2. **网络连接问题**
   ```bash
   # 使用代理或更换仓库
   gradle build --refresh-dependencies
   ```

3. **权限问题**
   ```bash
   chmod +x gradlew
   ```

4. **内存不足**
   ```bash
   # 增加gradle.properties中的内存设置
   org.gradle.jvmargs=-Xmx4G
   ```

## 📋 验证清单

构建完成后的验证步骤：

- [ ] JAR文件生成: `build/libs/randomlucky-1.21.1-1.0.0.jar`
- [ ] 文件大小: 应该 > 50KB
- [ ] ZIP结构: 使用 `jar tf` 命令检查内容
- [ ] Minecraft兼容: 在1.21.1环境中测试加载

## 🚀 自动化构建脚本

创建 `build.sh` 脚本：
```bash
#!/bin/bash
echo "开始构建RandomLucky Mod..."

# 检查Java版本
java -version | grep "21\." || {
    echo "错误: 需要Java 21"
    exit 1
}

# 清理并构建
./gradlew clean build --no-daemon

# 检查构建结果
if [ -f "build/libs/randomlucky-1.21.1-1.0.0.jar" ]; then
    echo "✅ 构建成功!"
    echo "📁 输出位置: $(pwd)/build/libs/"
    ls -la build/libs/*.jar
else
    echo "❌ 构建失败"
    exit 1
fi
```

## 📊 性能优化

### 加速构建
```bash
# 并行构建
./gradlew build --parallel --build-cache

# 离线模式 (依赖已下载)
./gradlew build --offline
```

---

**重要提示**: 首次构建可能需要15-30分钟下载所有依赖。后续构建会更快。