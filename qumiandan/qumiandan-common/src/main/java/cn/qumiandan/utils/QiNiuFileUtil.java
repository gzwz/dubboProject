package cn.qumiandan.utils;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：七牛云文件上传工具类
 * @author：zhuyangyong
 * @date: 2018/11/20 10:29
 */
@Slf4j
public class QiNiuFileUtil implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Value("${qiniu.accessKey}")
    private static String accessKey = "k8tVMp_lysmGPwnJxx0M74JHAkVM_iyvyre5F0nC";
//    @Value("${qiniu.secretKey}")
    private static String secretKey = "k0by60pDuyJUvLJdBJqgkayilpZOElaMT_K8kGLg";
//    @Value("${qiniu.bucket}")
    private static String bucket = "qmd-img";
//    @Value("${qiniu.domainOfBucket}")
    private static String domainOfBucket = "http://phr2czqqv.bkt.clouddn.com/";

    /**
     * 华东    Zone.zone0()
     * 华北    Zone.zone1()
     * 华南    Zone.zone2()
     * 北美    Zone.zoneNa0()
     */
    private static Zone zone = Zone.zone0();

    public static String getAccessKey() {
        return accessKey;
    }

    public static void setAccessKey(String accessKey) {
        QiNiuFileUtil.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String secretKey) {
        QiNiuFileUtil.secretKey = secretKey;
    }

    public static String getBucket() {
        return bucket;
    }

    public static void setBucket(String bucket) {
        QiNiuFileUtil.bucket = bucket;
    }

    public static String getDomainOfBucket() {
        return domainOfBucket;
    }

    public static void setDomainOfBucket(String domainOfBucket) {
        QiNiuFileUtil.domainOfBucket = domainOfBucket;
    }

    public static Zone getZone() {
        return zone;
    }

    public static void setZone(Zone zone) {
        QiNiuFileUtil.zone = zone;
    }

    /**
     * 获取上传凭证
     * @return
     */
    public static String getUploadCredential(){
        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
        long expireSeconds = 24 * 60 * 60;
        StringMap putPolicy = new StringMap();
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        log.info("upToken:" + upToken);
        return upToken;
    }

    /**
     * 获取文件的访问凭证
     * @return
     */
    public static Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 公有空间返回文件URL
     * @param fileName
     * @param domainOfBucket
     * @return
     */
    public  String publicFile(String fileName,String domainOfBucket) {
        String encodedFileName=null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        System.out.println(finalUrl);
        return finalUrl;
    }

    /**
     * 私有空间返回文件URL
     * @param auth
     * @param fileName
     * @param domainOfBucket
     * @param expireInSeconds
     * @return
     */
    public static String privateFile(Auth auth,String fileName,String domainOfBucket,long expireInSeconds) {
        String encodedFileName=null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
        	log.error("", e);
        }
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);
        return finalUrl;
    }

    /**
     * 获取文件信息
     * @param key
     * @return
     */
    public static FileInfo getFileInfo(String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        BucketManager bucketManager = new BucketManager(QiNiuFileUtil.getAuth(), cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(QiNiuFileUtil.getBucket(), key);
            return fileInfo;
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        getUploadCredential();
//        fileUpload(getUploadCredential(),"E:\\QQ图片20181112155959.png","QQ图片20181112155959");
//        fileUpload("E:\\test1113.png","test1113");
//        publicFile("test","C:\\Users\\94032\\Pictures\\IQIYISnapShot\\test.jpg");
//        privateFile(Auth.create("k8tVMp_lysmGPwnJxx0M74JHAkVM_iyvyre5F0nC", "k0by60pDuyJUvLJdBJqgkayilpZOElaMT_K8kGLg"),"test1113-7","http://phr2czqqv.bkt.clouddn.com/",3600);
//        getFileList(Zone.zone0(),Auth.create("k8tVMp_lysmGPwnJxx0M74JHAkVM_iyvyre5F0nC", "k0by60pDuyJUvLJdBJqgkayilpZOElaMT_K8kGLg"),"http://phr2czqqv.bkt.clouddn.com/","test",10,"");
//        getFileInfo(Zone.zone0(),"test1113",getAuth(),bucket);
    }
}
