package com.example.statup.module

/*
str_title",
str_main_stat",
main_stat_idx",
initial_level",
final_level",
str_description",
button_color_index",
progress_color_index
 */
data class StatItem(
    var str_stat_name : String? = "",
    var str_main_stat : String? = "",
    var main_stat_idx : Int = 0,
    var initial_level : Int = 1,
    var final_level : Int = 100,
    var cur_level : Int = initial_level,
    var str_description : String? = "",
    var button_color_index : Int = 0,
    var progress_color_index : Int = 0,
    var cur_exp_value : Int = 0,
    var max_exp_value : Int = 10
)
{
    fun isFinalLevel() : Boolean {return (final_level == initial_level)}
    fun plusExp()
    {
        ++cur_exp_value
        if(cur_exp_value >= max_exp_value)
        {
            cur_exp_value = 0
            ++cur_level

            if(cur_level > final_level)
            {
                cur_level = final_level
                cur_exp_value = max_exp_value
            }
        }
    }

    // we don't allow the user to down below levels (like game)
    fun minusExp()
    {
        --cur_exp_value
        if(cur_exp_value < 0)
        {
            cur_exp_value = 0
        }
    }
}
