package young.com.sonagibook_app

import androidx.room.Room
import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.*
import young.com.sonagibook_app.retrofit.RetrofitClient
import young.com.sonagibook_app.retrofit.RetrofitInterface
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDao
import young.com.sonagibook_app.room.TokenDatabase

class Repository {
    companion object{
        val instance = Repository()
    }
    private val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(
        RetrofitInterface::class.java)


    suspend fun getAccessToken(token : String) : Response<RetrofitGetResponseAllInfo>{
        return iRetrofit.getAccessToken(token)
    }

    suspend fun postNewNotice(token : String, noticeInfo : RetrofitPostNoticeDto){
        return iRetrofit.postNewNotice(token, noticeInfo)
    }

    suspend fun getNoticeList(page : Int, token : String) : Response<RetrofitResponseNoticeDto>{
        return iRetrofit.getNoticeList(page, token)
    }

    suspend fun postRefreshToken(refreshToken : HashMap<String,String>) : Response<RetrofitResponseRefreshTokenDto>{
        return iRetrofit.postRefreshToken(refreshToken)
    }

    suspend fun getNoticeContent(nid : String, token: String) : Response<RetrofitResponseNoticeContentDto>{
        return iRetrofit.getNoticeContent(nid, token)
    }

    suspend fun deleteNoticeItem(nid : String, token: String){
        return iRetrofit.deleteNoticeItem(nid, token)
    }

    suspend fun postNoticeLike(token : String, nid: String) : Response<RetrofitResponseNoticeLikeDto>{
        return iRetrofit.postNoticeLike(token, nid)
    }

    suspend fun postNoticeCancelLike(token : String, nid: String) : Response<RetrofitResponseNoticeLikeDto>{
        return iRetrofit.postNoticeCancelLike(token, nid)
    }


}