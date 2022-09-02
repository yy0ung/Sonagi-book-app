package young.com.sonagibook_app

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


    suspend fun UserApiClient.Companion.loginWithKakao(context: Context): OAuthToken {
        return if (instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk(context)
            } catch (error: Throwable) {
                // 사용자가 로그인을 취소한 경우
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error

                //카카오 계정 로그인을 시도
                UserApiClient.loginWithKakaoAccount(context)
            }
        } else {
            UserApiClient.loginWithKakaoAccount(context)
        }
    }

    //카카오톡으로 로그인
    suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken {
        return suspendCoroutine<OAuthToken> { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token)
                } else {
                    continuation.resumeWithException(RuntimeException("kakao access token 받기 실패"))
                }
            }
        }
    }

    //카카오 계정으로 로그인
    suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken {
        return suspendCoroutine<OAuthToken> { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token)
                } else {
                    continuation.resumeWithException(RuntimeException("kakao access token 받기 실패"))
                }
            }
        }
    }
