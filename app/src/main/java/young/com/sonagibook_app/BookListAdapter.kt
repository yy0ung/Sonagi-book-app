package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto

class BookListAdapter(private val bookList : HashMap<Int, ArrayList<Int>>)
    :RecyclerView.Adapter<BookListAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_box, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(bookList[position]!=null){
            val t = bookList[position]?.get(0)?.minus(9)
            Log.d(TAG, "onBindViewHolder: ㅇㅇㅇㅇㅇ $t")
            when {
                t?.equals(0) == true -> {
                    holder.a0.setBackgroundResource(R.drawable.schedule_event)
                }
                t?.equals(1) == true -> {
                    holder.a1.setBackgroundResource(R.drawable.schedule_event)
                }
                t?.equals(2) == true -> {
                    holder.a2.setBackgroundResource(R.drawable.schedule_event)
                }
                t?.equals(3) == true -> {
                    holder.a3.setBackgroundResource(R.drawable.schedule_event)
                }
                t?.equals(4) == true -> {
                    holder.a4.setBackgroundResource(R.drawable.schedule_event)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return 7
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val a0 : LinearLayout = itemView.findViewById(R.id.grid0)
        val a1 : LinearLayout = itemView.findViewById(R.id.grid1)
        val a2 : LinearLayout = itemView.findViewById(R.id.grid2)
        val a3 : LinearLayout = itemView.findViewById(R.id.grid3)
        val a4 : LinearLayout = itemView.findViewById(R.id.grid4)
        val a5 : LinearLayout = itemView.findViewById(R.id.grid5)
        val a6 : LinearLayout = itemView.findViewById(R.id.grid6)
        val a7 : LinearLayout = itemView.findViewById(R.id.grid7)
        val a8 : LinearLayout = itemView.findViewById(R.id.grid8)
        val a9 : LinearLayout = itemView.findViewById(R.id.grid9)
        val a10 : LinearLayout = itemView.findViewById(R.id.grid10)
        val a11 : LinearLayout = itemView.findViewById(R.id.grid11)
        val a12 : LinearLayout = itemView.findViewById(R.id.grid12)
        val a13 : LinearLayout = itemView.findViewById(R.id.grid13)
        val a14 : LinearLayout = itemView.findViewById(R.id.grid14)
        val a15 : LinearLayout = itemView.findViewById(R.id.grid15)
        val a16 : LinearLayout = itemView.findViewById(R.id.grid16)
        val a17 : LinearLayout = itemView.findViewById(R.id.grid17)
        val a18 : LinearLayout = itemView.findViewById(R.id.grid18)
        val a19 : LinearLayout = itemView.findViewById(R.id.grid19)
        val a20 : LinearLayout = itemView.findViewById(R.id.grid20)
        val a21 : LinearLayout = itemView.findViewById(R.id.grid21)
        val a22 : LinearLayout = itemView.findViewById(R.id.grid22)
    }



}