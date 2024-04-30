package com.dostonbek.phonecall
import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contactsList: List<Contact> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(contactsList: List<Contact>) {
        this.contactsList = contactsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactNameTextView: TextView = itemView.findViewById(R.id.contact_name_textView)
        private val contactPhoneNumberTextView: TextView = itemView.findViewById(R.id.contact_phone_number_textView)
        private val contactImageView: ImageView = itemView.findViewById(R.id.contact_imageView)

        init {
            itemView.setOnClickListener {
                onItemClick(contactsList[adapterPosition].phoneNumber)
            }
        }

        fun bind(contact: Contact) {
            contactNameTextView.text = contact.name
            contactPhoneNumberTextView.text = contact.phoneNumber

            // Load contact image if available
            contactImageView.setImageURI(contact.photoUri)
        }
    }
}
