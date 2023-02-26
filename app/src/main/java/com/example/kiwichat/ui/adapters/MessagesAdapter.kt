import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiwichat.R
import com.example.kiwichat.data.Messages
import com.google.firebase.auth.FirebaseAuth

class MessagesAdapter (val context: Context, val messageList:MutableList<Messages>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_RECEIVED = 1
    var ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return ReceivedViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            holder as SentViewHolder
            holder.sentMessage.text = currentMessage.text
        }else{
            holder as ReceivedViewHolder
            holder.receivedMessage.text = currentMessage.text
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid == currentMessage.id ){
            return ITEM_RECEIVED
        }else{
            return ITEM_SENT
        }
    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.tv_sentMessage)
    }

    class ReceivedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.tv_receivedMessage)
    }
}