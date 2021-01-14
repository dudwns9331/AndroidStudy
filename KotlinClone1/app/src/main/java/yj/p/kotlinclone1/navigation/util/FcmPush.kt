package yj.p.kotlinclone1.navigation.util

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import okhttp3.*
import yj.p.kotlinclone1.navigation.model.PushDTO
import java.io.IOException

class FcmPush {
    var JSON = MediaType.parse("application/json; charset=utf-8")
    var url = "https://fcm.googleapis.com/fcm/send"
    var serverkey =
        "AAAARphfEmA:APA91bH93m-nMWVnMq-LUOMuTHXkDUifwTW9exsIlxirR8kEF8_NgWwVuEKuO64yx-8MQOrKkxRwNaWRZgKOymodR3SP9pgu9UCnVbEEE9F2QbrYMjp9lE55GWEZ4HqcblGIelSeO8nt"

    var gson: Gson? = null
    var okHttpClient: OkHttpClient? = null

    companion object {
        var instance = FcmPush()
    }

    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationUid: String, title: String, message: String) {
        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var token = task?.result?.get("pushToken").toString()

                    var pushDTO = PushDTO()
                    pushDTO.to = token
                    pushDTO.notification.title = title
                    pushDTO.notification.body = message

                    var body = RequestBody.create(JSON, gson?.toJson(pushDTO))
                    var request = Request
                        .Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "key=" + serverkey)
                        .url(url)
                        .post(body)
                        .build()

                    okHttpClient?.newCall(request)?.enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            println(response?.body()?.string())
                        }
                    })
                }
            }
    }
}