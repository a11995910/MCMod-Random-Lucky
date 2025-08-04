# Random Lucky Mod

一个为Minecraft 1.20.1添加创意合成和强化机制的模组。

## 🎯 项目概述

Random Lucky Mod为Minecraft带来了五个独特的游戏机制：
- **随机物品合成**：使用木棍创造惊喜
- **装备强化系统**：让你的装备变得更强
- **血量增强面包**：永久提升生命值上限
- **超级苹果**：获得永久的强力效果
- **大胃王**：永久增加饱食度上限

## ✨ 核心功能

### 1. 随机物品合成 🎲
- **合成方式**：在3×3工作台中放入9个木棍
- **效果**：随机获得一个物品
- **可能物品**：钻石、绿宝石、黄金、铁锭、红石等30+种物品

### 2. 装备强化系统 ⚔️
- **合成方式**：将三个相同装备在工作台中横向排列
- **效果**：中间装备获得强化，属性和耐久度翻倍
- **强化上限**：最多可强化9次
- **适用装备**：
  - 武器（剑、斧、三叉戟）
  - 工具（镐、铲、锄）
  - 护甲（头盔、胸甲、护腿、靴子）
  - 弓弩
- **耐久度强化**：每次强化耐久度翻倍，保持当前损坏比例

### 3. 血量增强面包 ❤️
- **合成方式**：钻石为中心，周围8个面包
- **效果**：食用后永久增加1颗心（2点血量）
- **上限**：最多增加400颗心

### 4. 超级苹果 🍎
- **合成方式**：钻石为中心，周围8个苹果
- **效果**：食用后获得永久的夜视、抗火、急速效果
- **特性**：每个玩家只能食用一次，多次食用不叠加

### 5. 大胃王 🍽️
- **合成方式**：腐肉 + 小麦种子 + 木棍（横向排列）
- **效果**：食用后永久增加1格饱食度上限
- **特性**：
  - 可以多次食用，没有上限限制
  - 恢复4点饱食度，饱和度修饰符0.6f
  - 使用NBT数据存储，确保数据持久化
  - 食用后显示增加信息和当前总额外上限

## 🛠️ 技术栈

- **Minecraft版本**：1.20.1
- **Forge版本**：47.3.0
- **Java版本**：17
- **开发工具**：Gradle 8.0+

## 📁 项目结构

```
randomlucky/
├── src/main/java/com/randomlucky/mod/
│   ├── RandomLuckyMod.java           # 主模组类
│   ├── common/
│   │   ├── registry/
│   │   │   ├── ModItems.java         # 物品注册
│   │   │   └── ModRecipes.java       # 配方注册
│   │   ├── item/
│   │   │   ├── HealthBreadItem.java  # 血量面包物品
│   │   │   └── EnhancementUtils.java # 强化工具类
│   │   ├── recipe/
│   │   │   ├── RandomItemRecipe.java # 随机物品配方
│   │   │   └── EnhancementRecipe.java # 强化配方
│   │   └── event/
│   │       ├── EnhancementEventHandler.java # 强化事件处理
│   │       └── TooltipEventHandler.java     # 提示事件处理
├── src/main/resources/
│   ├── META-INF/mods.toml            # 模组配置
│   ├── assets/randomlucky/           # 资源文件
│   └── data/randomlucky/             # 数据文件
└── build.gradle                     # 构建配置
```

## 🚀 项目启动

### 环境要求
- **Java版本**：17 或更高版本
- **操作系统**：Windows、macOS、Linux
- **IDE推荐**：IntelliJ IDEA、Eclipse、VS Code
- **内存要求**：至少4GB RAM

### 快速开始
1. **克隆项目**
   ```bash
   git clone https://github.com/a11995910/MCMod-Random-Lucky.git
   cd MCMod-Random-Lucky
   ```

2. **环境设置**
   ```bash
   # 确保Java 17已安装
   java -version
   
   # 设置项目权限（macOS/Linux）
   chmod +x gradlew
   
   # 初始化项目
   ./gradlew setup
   ```

3. **IDE配置**
   ```bash
   # 生成IntelliJ IDEA配置
   ./gradlew genIntellijRuns
   
   # 生成Eclipse配置
   ./gradlew genEclipseRuns
   ```

### 开发命令
- **客户端测试**：`./gradlew runClient`
- **服务器测试**：`./gradlew runServer`
- **数据生成**：`./gradlew runData`
- **构建模组**：`./gradlew build`
- **清理项目**：`./gradlew clean`
- **刷新依赖**：`./gradlew --refresh-dependencies`

### 代码规范
- 使用驼峰命名法
- 中文注释
- 不使用缩写
- 遵循Minecraft Forge最佳实践

## 🧪 项目测试

### 自动化测试
```bash
# 运行所有测试
./gradlew test

# 运行特定测试类
./gradlew test --tests "com.randomlucky.mod.*"

# 生成测试报告
./gradlew test jacocoTestReport
```

### 功能测试

#### 1. 客户端测试
```bash
# 启动开发客户端
./gradlew runClient
```

**测试清单**：
- [ ] 随机物品合成功能
- [ ] 装备强化系统
- [ ] 血量增强面包效果
- [ ] 超级苹果永久效果
- [ ] 大胃王饱食度增加
- [ ] 物品贴图和模型显示
- [ ] 本地化文本显示
- [ ] 工具提示信息

#### 2. 服务器测试
```bash
# 启动开发服务器
./gradlew runServer
```

**测试清单**：
- [ ] 多人游戏兼容性
- [ ] 数据同步功能
- [ ] 服务器性能影响
- [ ] 配方注册正确性

#### 3. 集成测试
```bash
# 构建并测试完整mod
./gradlew build
# 将生成的JAR文件放入真实Minecraft环境测试
```

### 测试环境
- **Minecraft版本**：1.20.1
- **Forge版本**：47.3.0+
- **Java版本**：17+
- **内存分配**：建议4GB+

### 调试工具
```bash
# 启用调试模式
./gradlew runClient --debug-jvm

# 查看详细日志
./gradlew runClient --info

# 性能分析
./gradlew runClient --profile
```

## 📦 项目部署

### 构建发布版本
```bash
# 清理并构建
./gradlew clean build

# 构建成功后，JAR文件位于：
# build/libs/randomlucky-1.20.1-1.1.0.jar
```

### 版本发布流程

#### 1. 准备发布
```bash
# 更新版本号（在build.gradle中）
# 更新CHANGELOG.md
# 确保所有测试通过
./gradlew test

# 构建最终版本
./gradlew clean build
```

#### 2. 质量检查
```bash
# 代码质量检查
./gradlew check

# 依赖安全扫描
./gradlew dependencyCheckAnalyze

# 生成文档
./gradlew javadoc
```

#### 3. 发布到平台

**CurseForge发布**：
1. 登录CurseForge开发者控制台
2. 上传构建的JAR文件
3. 填写版本说明和更新日志
4. 设置兼容的Minecraft和Forge版本
5. 发布版本

**Modrinth发布**：
1. 登录Modrinth开发者面板
2. 创建新版本
3. 上传JAR文件和截图
4. 填写详细的版本信息
5. 发布版本

**GitHub Release**：
```bash
# 创建Git标签
git tag -a v1.1.0 -m "Release version 1.1.0"
git push origin v1.1.0

# 在GitHub上创建Release，上传JAR文件
```

### 部署检查清单
- [ ] 版本号正确更新
- [ ] 所有功能测试通过
- [ ] 兼容性测试完成
- [ ] 文档更新完整
- [ ] 更新日志编写
- [ ] JAR文件构建成功
- [ ] 文件大小合理（< 10MB）
- [ ] 依赖关系正确
- [ ] 许可证信息完整

### 发布后验证
```bash
# 下载发布的JAR文件
# 在干净的Minecraft环境中测试
# 验证所有功能正常工作
# 检查是否有兼容性问题
```

### 回滚计划
如果发现严重问题：
1. 立即从发布平台撤回版本
2. 修复问题并重新测试
3. 发布修复版本
4. 通知用户更新

### 监控和维护
- 监控用户反馈和问题报告
- 定期检查兼容性
- 及时修复发现的bug
- 计划功能更新和改进

## 📋 开发事项

### ✅ 已完成
- [x] 项目基础框架搭建
- [x] 随机物品合成系统
- [x] 装备强化系统
- [x] 血量增强面包功能
- [x] 本地化支持（中文/英文）
- [x] 物品模型和贴图
- [x] 修复1.21.1 API兼容性问题
- [x] 解决运行时崩溃问题
- [x] 成功构建可运行的mod JAR文件
- [x] 适配Minecraft 1.20.1版本
- [x] 大胃王功能开发完成
- [x] 版本更新到v1.1.0
- [x] 完善项目文档和开发计划
- [x] 修复血量增强面包死亡后消失的bug (v1.1.1)
- [x] 实现血量数据NBT持久化存储
- [x] 添加玩家事件处理器（重生、登录、切换维度）

### 🔄 进行中
- [ ] 最终测试验证
- [ ] 大胃王功能完整测试

### 📝 待办事项
- [ ] 服务器兼容性测试
- [ ] 性能优化
- [ ] 更多随机物品
- [ ] 配置文件支持
- [ ] 成就系统集成
- [ ] 大胃王功能的平衡性调整
- [ ] 添加更多饱食度相关功能

## 🎮 游戏机制详解

### 强化系统算法
- **强化等级**：1-9级
- **属性倍数**：2^强化等级
- **强化方式**：三个相同装备横排合成
- **显示效果**：物品名称前添加[+等级]前缀

### 随机物品权重
所有物品等权重随机，包括：
- 矿物类：钻石、绿宝石、金锭等
- 材料类：红石、青金石、火药等
- 食物类：面包、苹果、胡萝卜等
- 工具材料：羽毛、皮革、线等

## 🔧 配置说明

### mods.toml配置
- **模组ID**：randomlucky
- **显示名称**：Random Lucky Mod
- **版本**：1.1.0
- **依赖**：Forge 47+, Minecraft 1.20.1+

### 本地化
支持简体中文(zh_cn)和英文(en_us)。

## 🐛 已知问题

- ~~强化装备的耐久度显示可能需要刷新~~ （已修复）
- ~~某些模组物品可能不兼容强化系统~~ （已修复）
- ~~血量增强在某些情况下可能需要重新登录生效~~ （已修复）
- ~~运行时崩溃问题~~ （已修复，现已可正常运行）
- ~~血量增强面包在死亡后增加的血量上限会消失~~ （已修复 v1.1.1）

### 🔧 最新修复 (v1.1.1)
- **血量增强面包持久化问题**：现在使用NBT数据存储血量增强效果，确保死亡重生、重新登录、切换维度后血量上限不会丢失
- **事件处理优化**：添加了完整的玩家事件处理器，自动恢复血量增强效果
- **用户体验改进**：食用血量面包后会显示当前总额外血量信息

## 🔧 安装使用

### 系统要求
- Minecraft 1.20.1
- Minecraft Forge 47.3.0+
- Java 17+

### 安装步骤
1. 下载最新版本的mod JAR文件：`randomlucky-1.20.1-1.1.0.jar`
2. 确保已安装Minecraft Forge 47.3.0或更高版本
3. 将JAR文件放入Minecraft mods文件夹
4. 启动游戏即可使用

## 🤝 贡献指南

### 参与方式
1. **Fork本项目**到你的GitHub账户
2. **克隆Fork的仓库**到本地
   ```bash
   git clone https://github.com/your-username/MCMod-Random-Lucky.git
   ```
3. **创建功能分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```
4. **进行开发**并遵循代码规范
5. **提交更改**
   ```bash
   git add .
   git commit -m "feat: 添加新功能描述"
   ```
6. **推送到你的Fork**
   ```bash
   git push origin feature/your-feature-name
   ```
7. **创建Pull Request**到主仓库

### 提交规范
使用[Conventional Commits](https://www.conventionalcommits.org/)格式：
- `feat:` 新功能
- `fix:` 修复bug
- `docs:` 文档更新
- `style:` 代码格式调整
- `refactor:` 代码重构
- `test:` 测试相关
- `chore:` 构建过程或辅助工具的变动

### 代码审查
- 所有PR都需要经过代码审查
- 确保代码通过所有测试
- 遵循项目的编码规范
- 添加必要的文档和注释

### 问题报告
使用GitHub Issues报告问题时，请包含：
- 详细的问题描述
- 复现步骤
- 预期行为vs实际行为
- 环境信息（Minecraft版本、Forge版本、Java版本）
- 相关日志和截图

## 📄 许可证

All Rights Reserved

## 📞 联系方式

- 问题反馈：请在GitHub Issues中提交
- 功能建议：欢迎在Issues中讨论

---

*该模组旨在为Minecraft带来更多趣味性和策略性的游戏体验。*