#!/bin/bash

echo "🔧 RandomLucky Mod 构建脚本"
echo "==============================="

# 检查当前目录
if [ ! -f "build.gradle" ]; then
    echo "❌ 错误: 请在项目根目录运行此脚本"
    exit 1
fi

# 检查Java版本
echo "📋 检查Java环境..."
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" != "21" ]; then
    echo "⚠️  警告: 建议使用Java 21，当前版本: $JAVA_VERSION"
    echo "   请设置JAVA_HOME到Java 21路径"
fi

# 检查Gradle
echo "📋 检查Gradle..."
if [ ! -f "gradlew" ]; then
    echo "❌ 错误: gradlew文件不存在"
    exit 1
fi

# 设置权限
chmod +x gradlew

echo "🚀 开始构建..."
echo "这可能需要几分钟时间，请耐心等待..."

# 清理旧构建
echo "🧹 清理旧文件..."
./gradlew clean --no-daemon

# 构建项目
echo "🔨 编译和打包..."
./gradlew build --no-daemon --info

# 检查构建结果
echo "📁 检查构建结果..."
BUILD_DIR="build/libs"
JAR_FILE="randomlucky-1.21.1-1.0.0.jar"

if [ -f "$BUILD_DIR/$JAR_FILE" ]; then
    echo ""
    echo "✅ 构建成功!"
    echo "==============================="
    echo "📦 输出文件:"
    echo "   主JAR: $(pwd)/$BUILD_DIR/$JAR_FILE"
    echo "   大小: $(du -h "$BUILD_DIR/$JAR_FILE" | cut -f1)"
    echo ""
    echo "📂 所有构建文件:"
    ls -la $BUILD_DIR/
    echo ""
    echo "🎯 安装说明:"
    echo "   将 $JAR_FILE 复制到你的 Minecraft mods 目录"
    echo "   例如: ~/.minecraft/mods/"
    echo ""
else
    echo ""
    echo "❌ 构建失败!"
    echo "请检查上方的错误信息"
    echo ""
    echo "💡 常见解决方案:"
    echo "   1. 确保网络连接正常"
    echo "   2. 检查Java版本是否为21"
    echo "   3. 删除 ~/.gradle/caches 重试"
    exit 1
fi

echo "🎉 构建完成!"