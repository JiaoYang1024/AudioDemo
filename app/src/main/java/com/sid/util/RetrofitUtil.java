package com.sid.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者： ZlyjD.
 * 时间：2017/6/28.
 */

public class RetrofitUtil {
    private volatile static RetrofitUtil uniqueInstance = null;
    private RetrofitApi api;

    public static RetrofitUtil getInstance() {
        if (uniqueInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new RetrofitUtil();
                }
            }
        }
        return uniqueInstance;
    }

//众联证书方法  不是鸿洋的，另一个人
    /*public static SSLSocketFactory getSSLSocketFactory(Context context){
        try {
            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream instream = context.getResources()
                    .openRawResource(R.raw.zlyijia);
            ksTrust.load(instream, "123456".toCharArray());
            //TrustManager decides which certificate authorities to use.
            TrustManagerFactory tmf = TrustManagerFactory  .getInstance("X509");//TrustManagerFactory.getDefaultAlgorithm()
            tmf.init(ksTrust);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return  sslContext.getSocketFactory();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    //众联证书方法  收藏人的
    /*protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("BKS");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {
String s = e.getMessage();
        }
        return null;
    }*/


    //信任所有证书方法
    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }

//众联证书方法  鸿洋的，
   /*public static SSLSocketFactory getSSLSocketFactory(InputStream... certificates){



       try {
           CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
           KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
           keyStore.load(null);
           int index = 0;
           for (InputStream certificate : certificates)
           {
               String certificateAlias = Integer.toString(index++);
               keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));


                   if (certificate != null){

                       certificate.close();
                   }

           }

           SSLContext sslContext = SSLContext.getInstance("TLS");

           TrustManagerFactory trustManagerFactory =
                   TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

           trustManagerFactory.init(keyStore);

           sslContext.init
                   (
                           null,
                           trustManagerFactory.getTrustManagers(),
                           new SecureRandom()
                   );

         return sslContext.getSocketFactory();

       } catch (CertificateException e) {
           e.printStackTrace();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (KeyStoreException e) {
           e.printStackTrace();
       } catch (KeyManagementException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

return null;
   }*/







    private RetrofitUtil() {
        //创建OkHttpClient对象
        OkHttpClient okHttpClient = null;
        //   int[] certficates = new int[]{R.raw.zlyijia_com};
        //  try {
        try {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    //  .sslSocketFactory(getSSLSocketFactory(InstallApplication.getContext().getAssets().open("srca.cer")))
                    //   .sslSocketFactory(getSSLSocketFactory(InstallApplication.getContext().getAssets().open("dingjianlei.crt"),InstallApplication.getContext().getAssets().open("dingjianlei33.crt")))
                    // .sslSocketFactory(getSSLSocketFactory(new Buffer().writeUtf8(CER).inputStream(),new Buffer().writeUtf8(CER_1).inputStream(),new Buffer().writeUtf8(CER_2).inputStream()))
                    //.sslSocketFactory(getSSLSocketFactory(InstallApplication.getContext()))
                 //   .sslSocketFactory(getSSLSocketFactory())   //放开了
                  //  .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)  //放开了
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  } catch (IOException e) {
            e.printStackTrace();
        }*/


      /*  try {
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //创建Gson对象
        Gson gson = new GsonBuilder().create();
        //创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .build();


        //retrofit对象调用Create方法,生成接口对象
        api = retrofit.create(RetrofitApi.class);
    }



    public void getIndentEvaluation(String accountId, String indentId, String token,
                                    Observer<String> subscriber) {
        api.getIndentEvaluation(accountId, indentId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void testPost(int p,Observer<String> subscriber) {
        api.testPost(p)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void testGet(Observer<String> subscriber) {
        //api.test("http://gank.io/api/today")
        api.test("https://wanandroid.com/wxarticle/chapters/json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }





}
