package com.example.statup.module


data class StatItem(
    var str_stat_name : String = "",
    var initial_level : Int = 1,
    var final_level : Int = 100,
    var str_main_stat : String = "",
    var level : Int = 1,
    var cur_exp_value : Int = 1,
    var max_exp_value : Int = 10
)
