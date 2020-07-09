package com.dnavarro.askanswerviews.retrofit

import android.app.Application
import android.content.Context
import com.dnavarro.askanswerviews.MainActivity
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object serviceLoginResponse {
//    USE YOUR URL CHANGE IT
    private const val IP: String = "analyticsmg.herokuapp.com"
    private const val URL: String = "https://analyticsmg.herokuapp.com/movil/"
    lateinit var context: Application

    const val URI = "https://analyticsmg.herokuapp.com/"

    fun init(app: Application){
        this.context = app
        storeC = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this.context.applicationContext))
        intercep = HttpLoggingInterceptor()
        intercep.level = HttpLoggingInterceptor.Level.BODY

        val Nullh: HostnameVerifier = object: HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                println("hostName: $hostname")
                println("session: $session")
                return hostname == IP
            }
        }

        val xma: X509TrustManager = object: X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                println("auth type: " + authType)
                chain!!.forEach {
                    println(it)
                }

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

                println("auth type: " + authType)
                chain!!.forEach {
                    println(it)
                }

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {

                return emptyArray()
            }
        }

        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        println("directorio: " + File("cert.pem").getAbsolutePath())
        val caInput: InputStream = BufferedInputStream(context.assets.open("cert.cer"))
        val ca: X509Certificate = caInput.use {
            cf.generateCertificate(it) as X509Certificate
        }
        System.out.println("ca=" + ca.subjectDN)

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs inputStream our KeyStore
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val context2: SSLContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        client = OkHttpClient.Builder().addNetworkInterceptor(intercep).sslSocketFactory(context2.socketFactory,xma)
            .retryOnConnectionFailure(true).cookieJar(storeC).hostnameVerifier(Nullh).build()


        retrofit = Retrofit.Builder()

            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
//    private lateinit var cookie: CookieHandler

    private lateinit var storeC: ClearableCookieJar

    private lateinit var intercep: HttpLoggingInterceptor

    lateinit var client: OkHttpClient






    private lateinit var retrofit: Retrofit


    fun<T> buildService(Service: Class<T>): T {
        return retrofit.create(Service)
    }
}