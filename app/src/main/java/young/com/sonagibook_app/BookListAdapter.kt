package young.com.sonagibook_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto

class BookListAdapter(private val bookList : ArrayList<RetrofitResponseBookDto>)
    :RecyclerView.Adapter<BookListAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_box, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val b = bookList[0].start.substring(8,10)
        holder.box0.setBackgroundResource(R.drawable.schedule_prac)
    }

    override fun getItemCount(): Int {
        return 6
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val box0 : LinearLayout = itemView.findViewById(R.id.bookBox0)
        val box1 : LinearLayout = itemView.findViewById(R.id.bookBox1)
        val box2 : LinearLayout = itemView.findViewById(R.id.bookBox2)
        val box3 : LinearLayout = itemView.findViewById(R.id.bookBox3)
        val box4 : LinearLayout = itemView.findViewById(R.id.bookBox4)
        val box5 : LinearLayout = itemView.findViewById(R.id.bookBox5)
        val box6 : LinearLayout = itemView.findViewById(R.id.bookBox6)
    }



}