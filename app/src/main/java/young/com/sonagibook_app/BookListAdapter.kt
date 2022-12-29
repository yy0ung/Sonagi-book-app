package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto

class BookListAdapter(private val bookList : HashMap<Int, HashMap<String, ArrayList<String>>>, private val supportFragmentManager: FragmentManager)
    :RecyclerView.Adapter<BookListAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_box, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(bookList[position]!=null){
            //val t = bookList[position]?.get(0)?.minus(9)
            //bookList[position]?.keys == start time
            for(i in bookList[position]!!.keys){
                val start = i.toInt().minus(9)
                val end = bookList[position]!![i]!!.get(1).toInt().minus(9)
                var t = ArrayList<Int>()
                if(start==end){
                    t.add(start)
                }else{
                    for(i in start..end){
                        t.add(i)
                    }
                }

                when {
                    t.contains(0) -> {
                        val rid = bookList[position]!![i]!![3]!!
                        holder.a0.setBackgroundResource(R.drawable.schedule_event)
                        holder.a0.setOnClickListener {
                            val bottomSheet = BookContentDialog()
                            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                        }
                    }
                    t.contains(1) -> {
                        holder.a1.setBackgroundResource(R.drawable.schedule_event)
                        holder.a1.setOnClickListener {
                            val bottomSheet = BookContentDialog()
                            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                        }
                    }
                    t.contains(2) -> {
                        holder.a2.setBackgroundResource(R.drawable.schedule_event)
                        holder.a2.setOnClickListener {
                            val bottomSheet = BookContentDialog()
                            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                        }
                    }
                    t.contains(3) -> {
                        holder.a3.setBackgroundResource(R.drawable.schedule_event)
                        holder.a3.setOnClickListener {
                            val bottomSheet = BookContentDialog()
                            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                        }
                    }
                    t.contains(4) -> {
                        holder.a4.setBackgroundResource(R.drawable.schedule_event)
                    }
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
    }



}