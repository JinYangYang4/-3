package com.example.musicapp

fun Testinit1(): ArrayList<Music>{
    val musiclist = ArrayList<Music>()
    for (i in 1..50) {
        if (i%3 == 0) {
            val music = Music("歌曲 $i", "歌手$i", "pic", R.drawable.item_1, 123, "url")
            musiclist.add(music)
        }else{
            val music = Music("歌曲 $i", "歌手$i", "pic", R.drawable.item_3, 123, "url")
            musiclist.add(music)
        }

    }
    return musiclist
}
fun addActionselect(): ArrayList<String>{
    val charts = ArrayList<String>()
        //1:原创榜/2:新歌榜/3:飙升榜/4:热歌榜
    charts.add("原创榜")
    charts.add("新歌榜")
    charts.add("飙升榜")
    charts.add("热歌榜")
    charts.add("我的喜欢")
    charts.add("历史播放")
    charts.add("本地音乐")
    return charts
}
