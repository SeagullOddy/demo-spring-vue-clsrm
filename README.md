# Classroom: Spring & Vue Demo

课堂
Classroom：前后端分离的仿课堂派项目简陋版，基本功能完结。重制版见[此处](https://github.com/SeagullOddy/demo-spring-vue-clsrmphi)。

注意：

- 未实现`下次自动登录`功能，使用 JWT Token 进行登陆认证，Token 有效期为 10 小时。
- 项目使用七牛云的对象存储空间服务实现作业上传、下载功能，配置详见 `com.example.classroomschool.config.QiNiuConfig.java`。配置中的 `ACCESS_KEY`、`SECRET_KEY`、`BUCKET`、`CDN_PROFILE` 均已过时，因此想使用本项目的上传作业功能，需要自行在七牛云开通对象存储空间服务（自行百度教程）并修改 `QiNiuConfig.java` 中相应参数。
