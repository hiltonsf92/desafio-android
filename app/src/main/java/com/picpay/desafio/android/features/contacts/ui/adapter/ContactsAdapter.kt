package com.picpay.desafio.android.features.contacts.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ItemContactBinding
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.features.contacts.di.PicassoComponent
import com.picpay.desafio.android.shared.hidden
import com.picpay.desafio.android.shared.show
import com.squareup.picasso.Callback

class ContactsAdapter : ListAdapter<User, ContactsAdapter.ContactViewHolder>(ContactDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ContactViewHolder(
        private val binding: ItemContactBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            name.text = user.name
            username.text = user.username
            progressBar.show()
            PicassoComponent.instance
                .load(user.image)
                .error(R.drawable.ic_round_account_circle)
                .into(picture, object : Callback {
                    override fun onSuccess() = progressBar.hidden()
                    override fun onError(e: Exception?) = progressBar.hidden()
                })
        }
    }
}