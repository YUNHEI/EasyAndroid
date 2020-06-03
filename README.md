# EasyAndroid
一个完整基于kotlin的安卓开发框架,采用了mvvm设计模式。涵盖了：

1、基于retrofit2封装的通过kotlin协程实现的网络框架

2、基于阿里开源router修改的api-router实现项目模块化

3、基于glide的图片加载缓存框架 

4、基于room实现的往来数据缓存加载 

5、基于step实现的数据异步提交 

6、基于PreferenceHolder实现的本地数据快速存储 

7、基于mlist实现的简单复杂列表的快速开发扩展等等。。 

本框架几乎涵盖了开发所需的所有模块组件。简单fork之后就可以基于框架快速开发。

暂时先提交完整的框架代码，后续要完善本框架的用法。

## 框架说明：

#### 1. 下载运行
 1. 下载项目
 2. 点击Sync Now 同步项目，下载对应的第三方库， 项目使用的gradle比较新，下载时间可能略长，要耐心等待
 3. 运行项目app
 
#### 2. 简单页面创建
效果图  <img src = "/image/简单页面1.jpg" width = "200" height = "400" />
对应代码  <img src = "/image/简单页面代码1.png" width = "350" height = "400" />

##### 创建步骤 
 1. 新建 SimpleFirstFragment 继承 BaseSimpleFragment
 2. 添加注解 @Launch 
 3. 设置布局contentLayoutId 为自己创建的布局文件 例如 R.layout.fragment_1
 4. 实现 initAndObserve 初始化方法

在 initAndObserve 初始化方法 中可以设置toolbar样式 设置页面内容 设置点击事件等
页面跳转操作非常简单  startPage(SimpleSecondFragment::class)  其中 SimpleSecondFragment 就是对应页面的文件
，复杂跳转操作，带参数跳转等后面还将展开说明。






