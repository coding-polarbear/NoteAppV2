package kr.purplebeen.noteapp.features.detailview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ActivityDetailViewBinding
import kr.purplebeen.noteapp.features.edit.EditActivity

class DetailViewActivity : AppCompatActivity() {
    val position : Int by lazy {
        intent.extras.getInt("position")
    }

    lateinit var mBinding: ActivityDetailViewBinding
    lateinit var mViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_view)
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        mViewModel.loadData(position)
        observeViewModel()
    }

    fun observeViewModel() {
        mViewModel.navigateToEditCallback.observe(this, Observer {
            var newIntent  : Intent = Intent(this@DetailViewActivity, EditActivity::class.java)
            newIntent.putExtra("position", position)
            startActivity(newIntent)
        })

        mViewModel.finishCallback.observe(this, Observer {
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadData(position)
    }

}
