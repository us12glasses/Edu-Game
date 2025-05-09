package com.ghozi.game

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ghozi.game.databinding.GameItemRecyclerRowBinding

class GameListAdapter(private val gameModelList : List<GameModel>) :
    RecyclerView.Adapter<GameListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: GameItemRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GameModel){
            binding.apply {
                gameTitleText.text = model.title
                gameSubtitleText.text = model.subtitle
                gameTimeText.text = model.time + " min"
                root.setOnClickListener {
                    val intent = Intent(root.context, GameActivity::class.java)
                    GameActivity.questionModelList = model.questionList.toMutableList()
                    GameActivity.time = model.time
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GameItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gameModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(gameModelList[position])
    }

}