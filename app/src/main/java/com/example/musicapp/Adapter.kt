package com.example.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class TitleAdapter(private val musiclist : ArrayList<Music>): RecyclerView.Adapter<TitleAdapter.ViewHolder>(){
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val music_item_song : TextView = view.findViewById<TextView>(R.id.music_item_song)
        val music_item_sing : TextView = view.findViewById<TextView>(R.id.music_item_sing)
        val music_item_pic : ImageView = view.findViewById<ImageView>(R.id.music_item_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.title_recyclerview_item,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val musicMessage = musiclist[position]
        holder.music_item_song.text = musicMessage.song
        holder.music_item_sing.text = musicMessage.sing
        holder.music_item_pic.setImageResource(musicMessage.pic1)
    }
    override fun getItemCount() = musiclist.size
}

class FragmentStateAdapter(fragmentActivity : FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MusicTitle1()
            1 -> MusicTitle2()
            2 -> MusicTitle3()
            3 -> MusicTitle4()
            4 -> MusicTitle5()
            5 -> MusicTitle6()
            6 -> MusicTitle7()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
    override fun getItemCount() = 7
}