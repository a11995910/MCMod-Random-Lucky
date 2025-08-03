# 模组测试清单

## 🧪 功能测试

### 1. 随机物品合成测试
- [ ] 在3×3工作台放入9个木棍
- [ ] 验证是否产生随机物品
- [ ] 测试多次合成获得不同物品
- [ ] 确认所有可能物品都能生成

### 2. 装备强化系统测试
- [ ] **武器强化测试**
  - [ ] 三把相同剑横排强化
  - [ ] 检查攻击伤害是否翻倍
  - [ ] 验证强化等级显示
  - [ ] 测试连续强化至9级
  
- [ ] **工具强化测试**
  - [ ] 三把相同镐横排强化
  - [ ] 检查挖掘效果
  - [ ] 验证工具提示信息
  
- [ ] **护甲强化测试**
  - [ ] 三件相同护甲横排强化
  - [ ] 检查防御力增加
  - [ ] 验证护甲韧性提升

### 3. 血量增强面包测试
- [ ] 钻石+8面包合成血量面包
- [ ] 食用后检查血量是否增加1颗心
- [ ] 测试最大血量限制（400颗心）
- [ ] 验证血量永久性

## 🔧 技术测试

### 编译测试
- [ ] `./gradlew compileJava` - Java代码编译
- [ ] `./gradlew build` - 完整构建
- [ ] 检查JAR文件生成

### 运行时测试
- [ ] `./gradlew runClient` - 客户端启动
- [ ] `./gradlew runServer` - 服务器启动
- [ ] 模组正常加载
- [ ] 无错误日志

### 兼容性测试
- [ ] Minecraft 1.21.1兼容
- [ ] Forge 52.0.17+兼容
- [ ] 多人游戏测试
- [ ] 与其他模组兼容性

## 📋 部署步骤

### 开发环境设置
1. **安装Java 21**
   ```bash
   # macOS
   brew install openjdk@21
   
   # 设置JAVA_HOME
   export JAVA_HOME=/opt/homebrew/opt/openjdk@21
   ```

2. **安装Gradle 8.1.1**
   ```bash
   # 下载并解压Gradle
   wget https://services.gradle.org/distributions/gradle-8.1.1-bin.zip
   unzip gradle-8.1.1-bin.zip
   export PATH=$PATH:/path/to/gradle-8.1.1/bin
   ```

3. **项目初始化**
   ```bash
   cd randomlucky
   gradle wrapper --gradle-version 8.1.1
   ./gradlew setup
   ./gradlew genIntellijRuns
   ```

### 构建部署
1. **开发构建**
   ```bash
   ./gradlew build
   ```

2. **输出位置**
   - JAR文件：`build/libs/randomlucky-1.21.1-1.0.0.jar`
   - 开发环境：`build/libs/randomlucky-1.21.1-1.0.0-dev.jar`

3. **安装到Minecraft**
   - 将JAR文件复制到`.minecraft/mods/`目录
   - 确保Forge已安装

## 🐛 已知问题及解决方案

### 1. Gradle版本问题
- **问题**：Gradle版本不兼容
- **解决**：使用Gradle 8.1.1，避免使用8.14+

### 2. Java版本问题
- **问题**：Java版本不匹配
- **解决**：确保使用Java 21

### 3. Forge版本问题
- **问题**：Forge版本太新或太旧
- **解决**：使用Forge 52.0.17+

### 4. 依赖下载问题
- **问题**：Maven依赖下载失败
- **解决**：检查网络连接，使用代理或更换仓库

## 📊 测试报告模板

```
测试日期：____
测试者：____
Minecraft版本：1.21.1
Forge版本：52.0.17

功能测试结果：
□ 随机物品合成：通过/失败
□ 装备强化系统：通过/失败  
□ 血量增强面包：通过/失败

问题记录：
1. ____
2. ____

建议：
1. ____
2. ____
```

## 🚀 部署清单

- [ ] 代码审查完成
- [ ] 单元测试通过
- [ ] 功能测试通过
- [ ] 性能测试通过
- [ ] 文档更新完成
- [ ] 版本号确认
- [ ] 构建成功
- [ ] JAR文件验证
- [ ] 发布说明准备