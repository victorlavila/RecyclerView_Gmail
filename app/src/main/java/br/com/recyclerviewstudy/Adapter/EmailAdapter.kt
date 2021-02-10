package br.com.recyclerviewstudy.Adapter
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.view.menu.MenuView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import br.com.recyclerviewstudy.Model.Email
import br.com.recyclerviewstudy.R
import br.com.recyclerviewstudy.R.layout.email_item

class EmailAdapter(val emails: MutableList<Email>): RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(email_item, parent, false)
        return EmailViewHolder(view)
    }

    override fun getItemCount(): Int = emails.size

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
        holder.itemView.setOnClickListener {
           if (selectedItems.isNotEmpty())
            onItemClick?.invoke(position)
        }
        holder.itemView.setOnClickListener {
            onItemLongClick?.invoke(position)
        }
        if(currentSelectedPos == position) currentSelectedPos = -1
    }

    fun toggleSelection(position: Int) {
        currentSelectedPos = position
        if(selectedItems[position, false]) {
            selectedItems.delete(position)
            emails[position].selected = false
        } else{
            selectedItems.put(position, true)
            emails[position].selected = true
        }
        notifyItemChanged(position)
    }

    fun deleteEmails() {
        emails.removeAll(
                emails.filter { it.selected }
        )
        notifyDataSetChanged()
        currentSelectedPos = -1
    }

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtUser by lazy { itemView.findViewById<TextView>(R.id.txt_user) }
        val txtSubject by lazy { itemView.findViewById<TextView>(R.id.txt_subject) }
        val txtpreview by lazy { itemView.findViewById<TextView>(R.id.txt_preview) }
        val txtData by lazy { itemView.findViewById<TextView>(R.id.txt_date) }
        val txtStared by lazy { itemView.findViewById<ImageView>(R.id.img_star) }
        val txtIcon by lazy { itemView.findViewById<TextView>(R.id.txt_icon) }

        fun bind(email: Email) {
            with(email){
                val hash = user.hashCode()
                txtIcon.text = user.first().toString()
                txtIcon.background = itemView.oval(Color.rgb(hash,hash.green/2, hash.blue))
                txtUser.text = user
                txtSubject.text = subject
                txtpreview.text = preview
                txtData.text = date

                txtUser.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                txtSubject.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                txtData.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)

                txtStared.setImageResource(
                        if (stared) R.drawable.ic_baseline_star_rate_24
                        else R.drawable.ic_baseline_star_border_24
                )
                if(email.selected){
                    txtIcon.background = txtIcon.oval(
                            Color.rgb(26, 115, 233)
                    )
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.rgb(232, 240, 253))
                    }
                } else{
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.WHITE)
                    }
                }

                if(selectedItems.isNotEmpty()){
                    animated(txtIcon, email)
                }
            }
        }

        private fun animated(view: TextView, email: Email){
            val oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
            val oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)

            oa1.interpolator = DecelerateInterpolator()
            oa2.interpolator = AccelerateDecelerateInterpolator()

            oa1.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if(email.selected) view.text = "\u2713"
                    oa2.start()
                }
            })
            oa1.start()
        }

    }
}

fun View.oval(@ColorInt color: Int) : ShapeDrawable{
    val oval = ShapeDrawable(OvalShape())
    with(oval){
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = color
    }
    return oval
}