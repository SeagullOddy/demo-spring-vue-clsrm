package com.example.classroomschool.Controller.File;

public class QiNiuConfigBean {
    private static String accessKey = "RUaqNmO2fZCf-KEsN31bhQm6Y72rrqHnsdpeJbTG";
    private static String secretKey = "o1piUKXmuUWGukq3IEDDRjQsE1YlLxSbIy8ky5FZ";
    private static String bucket = "ketangphi";
    private static String cdnProfile = "rwplkrcxd.hn-bkt.clouddn.com";
    private static String protocol = "http://";


    public static String getAccessKey() {
        return accessKey;
    }

    public  void setAccessKey(String accessKey) {
        QiNiuConfigBean.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public  void setSecretKey(String secretKey) {
        QiNiuConfigBean.secretKey = secretKey;
    }

    public static String getBucket() {
        return bucket;
    }

    public  void setBucket(String bucket) {
        QiNiuConfigBean.bucket = bucket;
    }

    public static String getCdnProfile() {
        return cdnProfile;
    }

    public  void setCdnProfile(String cdnProfile) {
        QiNiuConfigBean.cdnProfile = cdnProfile;
    }

    public static String getProtocol() {
        return protocol;
    }

    public  void setProtocol(String protocol) {
        QiNiuConfigBean.protocol = protocol;
    }
}

