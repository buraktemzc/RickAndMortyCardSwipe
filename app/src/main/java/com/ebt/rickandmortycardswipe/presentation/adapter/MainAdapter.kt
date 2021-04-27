package com.ebt.rickandmortycardswipe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ebt.rickandmortycardswipe.R
import com.ebt.rickandmortycardswipe.data.model.Character
import com.ebt.rickandmortycardswipe.databinding.ItemCardViewBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MainViewHolder(val binding: ItemCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.characterName.text =
                binding.root.context.resources.getString(R.string.character_name, character.name)
            binding.characterStatus.text = binding.root.context.resources.getString(
                R.string.character_status,
                character.status
            )
            binding.characterLocation.text = binding.root.context.resources.getString(
                R.string.character_location,
                character.location?.name ?: " - "
            )
            Glide.with(binding.characterImage.context)
                .load(character.image)
                .into(binding.characterImage)
        }
    }
}