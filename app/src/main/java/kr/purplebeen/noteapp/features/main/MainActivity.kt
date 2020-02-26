package kr.purplebeen.noteapp.features.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.adapters.ListAdapter
import kr.purplebeen.noteapp.databinding.ActivityMainBinding
import kr.purplebeen.noteapp.features.add.AddActivity
import kr.purplebeen.noteapp.features.detailview.DetailViewActivity

class MainActivity : AppCompatActivity() {
    val DURATION_TIME : Long = 2000L
    var prevPressTime : Long = 0L

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.viewModel = mViewModel

        observeViewModel()
        mViewModel.initPultusORM()
        setListView()
    }

    fun observeViewModel() {
        mViewModel.addButtonCallback.observe(this, Observer {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        })

        mViewModel.clickedPostionLiveData.observe(this, Observer{position ->
            var intent : Intent = Intent(this@MainActivity, DetailViewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        })
    }


    fun setListView() {
        mBinding.listView.adapter = ListAdapter(applicationContext, mViewModel.noteList)
        mBinding.listView.onItemClickListener = mViewModel.onItemClickListener
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - prevPressTime < DURATION_TIME) {
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        } else {
            prevPressTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, resources.getString(R.string.backpress_ment), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        mViewModel.refreshData()
        setListView()
    }
}
