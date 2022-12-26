package young.com.sonagibook_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityNoticeContentBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeLikeDto
import young.com.sonagibook_app.room.TokenDatabase

class NoticeContentActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    private lateinit var binding : ActivityNoticeContentBinding
    private lateinit var viewModel : NoticeContentViewModel
    private lateinit var viewModelFactory : NoticeContentViewModelFactory
    private var contentLike : Boolean? = false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = NoticeContentViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoticeContentViewModel::class.java)

        //toolbar
        setSupportActionBar(binding.noticeContentToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.noticeContentToolbar.setNavigationOnClickListener { onBackPressed() }

        val nid : String = intent.getStringExtra("nid")!!
        CoroutineScope(Dispatchers.Main).launch {

            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"

            getNoticeContent(nid, accessToken)
            viewModel.repositories1.observe(this@NoticeContentActivity){
                contentLike = it.data.liked
                binding.noticeContentTitle.text = it.data.title
                binding.noticeContentContent.text = it.data.content
                binding.noticeContentWriter.text = it.data.user.name.toString()
                binding.noticeContentLikeNum.text = it.data.likes.toString()
                binding.noticeContentConnectTitle.text = it.data.event?.title
                Log.d(TAG, "onCreate: 공지 작성자 ${it.data.user}")
                Log.d(TAG, "onCreate: 공지 연결 공지 ${it.data.event}")
                //type 다시 체크
                if(it.data.important==true){
                    binding.noticeContentImportant.visibility = View.VISIBLE
                    Log.d(TAG, "onCreate: 중요 공지")
                }
                if(it.data.liked == true){
                    binding.noticeContentLikeContainer.setBackgroundResource(R.drawable.noticecontent_like_active)
                }


                val createDate = it.data.createdAt.toString()
                val year = createDate.substring(0,4)
                val month = createDate.substring(5,7)
                val date = createDate.substring(8,10)
                binding.noticeContentDate.text = "${year}년 ${month}월 ${date}일"


                binding.noticeContentLikeContainer.setOnClickListener {
                    if(contentLike == true){
                        CoroutineScope(Dispatchers.IO).launch { postNoticeCancelLike(accessToken, nid) }
                        viewModel.repositories3.observe(this@NoticeContentActivity){ it2->
                            binding.noticeContentLikeNum.text = (it2.data?.likes).toString()
                            binding.noticeContentLikeContainer.setBackgroundResource(R.drawable.noticecontent_round_graybtn_item)
                        }
                    }else{
                        CoroutineScope(Dispatchers.IO).launch { postNoticeLike(accessToken, nid) }
                        viewModel.repositories2.observe(this@NoticeContentActivity){it3->
                            binding.noticeContentLikeNum.text = (it3.data?.likes?.plus(1)).toString()
                            binding.noticeContentLikeContainer.setBackgroundResource(R.drawable.noticecontent_like_active)

                        }
                }
                }

            }

            /*
            binding.delete.setOnClickListener {
                viewModel.deleteNoticeItem(nid, accessToken)
                Log.d(TAG, "onCreate: 삭제 성공")
            }

             */



//            binding.noticeContentLikeImg.setOnClickListener{
//                CoroutineScope(Dispatchers.IO).launch { postNoticeLike(accessToken, nid) }
//                viewModel.repositories2.observe(this@NoticeContentActivity){
//                    binding.noticeContentLikeNum.text = (it.data?.likes?.plus(1)).toString()
//                }
//
//            }
//            binding.noticeContentCancelLikeImg.setOnClickListener{
//                CoroutineScope(Dispatchers.IO).launch { postNoticeCancelLike(accessToken, nid) }
//                viewModel.repositories3.observe(this@NoticeContentActivity){
//                    binding.noticeContentLikeNum.text = (it.data?.likes).toString()
//                }
//
//            }

            }
        binding.noticeContentEditBtn.setOnClickListener {
            val intent = Intent(this@NoticeContentActivity, NoticeItemEditActivity::class.java)
            intent.putExtra("nid", nid)
            startActivity(intent)

        }




    }

    private suspend fun getNoticeContent(nid : String, token : String){
        viewModel.getNoticeContent(nid, token)
    }

    private suspend fun postNoticeLike(token: String, nid: String){
        val map = HashMap<String, String>()
        map["nid"] = nid
        viewModel.postNoticeLike(token, RetrofitPostNoticeLikeDto(map))
    }

    private suspend fun postNoticeCancelLike(token: String, nid: String){
        val map = HashMap<String, String>()
        map["nid"] = nid
        viewModel.postNoticeCancelLike(token, RetrofitPostNoticeLikeDto(map))
    }
}