package com.example.demo.cache;
import java.util.HashMap;
 
 //Description: 管理缓存 
 
 //可扩展的功能：当chche到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间 
 
public class CacheManager { 
    private volatile static  HashMap cacheMap ;
    //单实例构造方法 
    private CacheManager() { 
        super(); 
    } 
    
    public static HashMap getCache() {  
        if (cacheMap == null) {  
            synchronized (HashMap.class) {  
                if (cacheMap == null) { 
                    
                    cacheMap = new HashMap();  
                }  
            }  
        }  
        return cacheMap;  
    }
 
 
    //得到缓存。同步静态方法 
    private synchronized static Cache getCache(String key) { 
        return (Cache) getCache().get(key); 
    } 
 
    //判断是否存在一个缓存 
    public synchronized static boolean hasCache(String key) { 
        return getCache().containsKey(key); 
    } 
 
    //获取缓存信息 
    public static Cache getCacheInfo(String key) { 
 
        if (hasCache(key)) { 
            Cache cache = getCache(key); 
            if (cacheExpired(cache)) { //调用判断是否终止方法 
                cache.setExpired(true); 
                return null;
            } 
            return cache; 
        }else {
            return null;
        }
    }
    //获取缓存信息
    public static void delCacheInfo(String key) {
        cacheMap.remove(key);
    }

    //重写载入缓存信息方法 
    public static void putCacheInfo(String key,Object obj,long dt){ 
        Cache cache = new Cache(); 
        cache.setKey(key);
        if(dt<0){
            cache.setTimeOut(dt);
        }else {
            cache.setTimeOut(dt+System.currentTimeMillis());
        }
        cache.setValue(obj);
        cache.setExpired(false); 
        getCache().put(key,cache); 
    } 
 
    //判断缓存是否终止 
    public static boolean cacheExpired(Cache cache) { 
        if (null == cache) { //传入的缓存不存在 
            return false; 
        } 
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数 
        long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数
        if (cacheDt <= 0||cacheDt>nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE
            return false;
        } else { //大于过期时间 即过期
            return true;
        } 
    }
 
} 
 
 