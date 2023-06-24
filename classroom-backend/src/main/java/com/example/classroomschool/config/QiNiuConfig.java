package com.example.classroomschool.config;


import com.google.gson.Gson;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QiNiuConfig {

  private static final String ACCESS_KEY = "RUaqNmO2fZCf-KEsN31bhQm6Y72rrqHnsdpeJbTG";

  private static final String SECRET_KEY = "o1piUKXmuUWGukq3IEDDRjQsE1YlLxSbIy8ky5FZ";

  public static final String BUCKET = "ketangphi";

  /**
   * 七牛云的外链域名，测试域名，6/23日起30天有效
   */
  public static final String CDN_PROFILE = "rwplkrcxd.hn-bkt.clouddn.com";

  public static final String PROTOCOL = "http://";

  /**
   * 配置华南地区
   */
  @Bean
  public com.qiniu.storage.Configuration qiniuConfig() {
    return new com.qiniu.storage.Configuration(Region.region2());
  }

  /**
   * 构建一个七牛上传工具实例
   */
  @Bean
  public UploadManager uploadManager() {
    return new UploadManager(qiniuConfig());
  }

  /**
   * 认证信息实例
   */
  @Bean
  public Auth auth() {
    return Auth.create(ACCESS_KEY, SECRET_KEY);
  }

  /**
   * 构建七牛空间管理实例
   */
  @Bean
  public BucketManager bucketManager() {
    return new BucketManager(auth(), qiniuConfig());
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

}

