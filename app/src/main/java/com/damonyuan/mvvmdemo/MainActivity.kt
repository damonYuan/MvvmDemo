package com.damonyuan.mvvmdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.damonyuan.mvvmdemo.fragmentB.FragmentB
import com.damonyuan.mvvmdemo.fragmentA.FragmentA
import kotlinx.android.synthetic.main.activity_main.*

interface ICustomerName {
    fun getCustomerName(): String
}

class MainActivity : AppCompatActivity() {

    private val vm = ActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.text_view_info.text = vm.pageInfo

        val ft = supportFragmentManager.beginTransaction()
        val fragmentA = FragmentA()
        val fragmentB = FragmentB()
        ft.add(R.id.frameA, fragmentA)
        ft.add(R.id.frameB, fragmentB)
        ft.commit()
    }
}
