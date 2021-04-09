package kr.purplebeen.noteapp.features.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ActivityEditBinding

/**
 * Created by baehy on 2018. 1. 25..
 */
class EditActivity : AppCompatActivity() {
    lateinit var mViewModel: EditViewModel
    lateinit var mBinding:ActivityEditBinding
    val position : Int by lazy {
        intent.extras?.getInt("position") ?: -1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        mViewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        mBinding.viewModel = mViewModel
        mViewModel.loadData(position)
        observeViewModel()
    }

    fun observeViewModel() {
        mViewModel.saveButtonClickCallback.observe(this, Observer {
            mViewModel.save(mBinding.editTitle.text.toString(),
                            mBinding.editContent.text.toString())
        })

        mViewModel.finishCallback.observe(this, Observer {
            finish()
        })
    }
}