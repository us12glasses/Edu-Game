package com.ghozi.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.ghozi.game.databinding.FragmentHomePageBinding

class HomePage : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var gameModelList: MutableList<GameModel>
    private lateinit var adapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.GONE
        adapter = GameListAdapter(gameModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val gameModel = snapshot.getValue(GameModel::class.java)
                        if (gameModel != null) {
                            gameModelList.add(gameModel)
                        }
                    }
                }
                setupRecyclerView()
            }
    }
}
