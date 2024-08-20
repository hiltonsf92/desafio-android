package com.picpay.desafio.android.features.contacts.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.users.entities.User

object ContactDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}