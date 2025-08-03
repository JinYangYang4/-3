package com.example.musicapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.network.ApiResponse
import com.example.musicapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    val musiclist1 = ArrayList<Music>()
    val musiclist2 = ArrayList<Music>()
    val musiclist3 = ArrayList<Music>()
    val musiclist4 = ArrayList<Music>()
    private var requestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        for (t in 1..4) {
            RetrofitInstance.api.getMusic(t).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        apiResponse?.data?.forEach { song ->
                            Log.d(
                                "SongData",
                                "Song: ${song.song}, Singer: ${song.sing}, ID: ${song.id}  url: ${song.url} pic : ${song.pic}"
                            )
                            val music = Music(
                                song.song,
                                song.sing,
                                song.pic,
                                R.drawable.music,
                                song.id,
                                song.url
                            )
                            when (t) {
                                1 -> musiclist1.add(music)
                                2 -> musiclist2.add(music)
                                3 -> musiclist3.add(music)
                                4 -> musiclist4.add(music)
                            }
                        }
                    } else {
                        Log.e("MainActivity", "Error: ${response.errorBody()?.string()}")
                    }
                    requestCount++
                    if (requestCount == 4) {
                        initView()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("SongData", "Error: ${t.message}")
                    requestCount++
                    if (requestCount == 4) {
                        initView()
                    }
                }
            })
        }
    }

    private fun initView() {
        getmusic(musiclist1, musiclist2, musiclist3, musiclist4)

        val action_bar_recyclerview: RecyclerView =
            findViewById<RecyclerView>(R.id.action_bar_recyclerview)
        val layoutmanager = LinearLayoutManager(this)
        layoutmanager.orientation = LinearLayoutManager.HORIZONTAL
        action_bar_recyclerview.layoutManager = layoutmanager
        val adapter = ActionbarAdapter1(addActionselect())
        action_bar_recyclerview.adapter = adapter
        val viewpager2: ViewPager2 = findViewById<ViewPager2>(R.id.viewpager2)
        viewpager2.adapter = FragmentStateAdapter(this)

        // 设置点击上方RecyclerView切换下方ViewPager2内容
        adapter.setOnItemClickListener(object : ActionbarAdapter1.OnItemClickListener {
            override fun onItemClick(position: Int) {
                viewpager2.currentItem = position
                adapter.setSelectedPosition(position)
            }
        })

        // 确保启动时ViewPager2能正确显示内容，并强制刷新第一页Fragment的Adapter
        viewpager2.post {
            val fragment = supportFragmentManager.findFragmentByTag("f0")
            if (fragment != null && fragment.view != null) {
                val recyclerView = fragment.requireView()
                    .findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.title_recyclerview)
                recyclerView?.adapter?.notifyDataSetChanged()
            }
            viewpager2.currentItem = 0
        }

        // 初始化底部导航栏（3个选项的逻辑）
        val bottomNavigationView =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // 第一个按钮：首页逻辑（保持页面不动或跳转到首页）
                    true
                }

                R.id.nav_discover -> {
                    // 第二个按钮：音乐，点击跳转到播放页
                    navigateToMusicPlayer()
                    true
                }

                R.id.nav_profile -> {
                    // 第三个按钮：我的页面逻辑
                    true
                }

                else -> false
            }
        }

        // 原有网络请求代码（不变）
        for (t in 1..4) {
            // ... 你的网络请求代码 ...
        }
    }

    // 添加跳转方法：跳转到音乐播放页
    private fun navigateToMusicPlayer() {
        val intent = Intent(this, MusicPlayerActivity::class.java)
        // 可选：如果需要传递当前选中的音乐数据，在这里添加
        //        // 例如：intent.putExtra("music_url", selectedMusicUrl)
        startActivity(intent)
        // 可选：添加页面切换动画

    }
}