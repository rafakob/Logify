# Logify
Display Android logs in runtime


[ ![Download](https://api.bintray.com/packages/rafakob/maven/Logify/images/download.svg) ](https://bintray.com/rafakob/maven/Logify/_latestVersion)

```
implementation 'com.rafakob:logify:latest'
implementation 'com.rafakob:logify-okhttp3:latest'
```


```java
// App.java
Logify.init(this);


// OkHttp
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .addInterceptor(new LogifyInterceptor())
    .build();


// Show list of logs
Logify.startActivity(context);
```
