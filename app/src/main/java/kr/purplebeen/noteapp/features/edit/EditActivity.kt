package kr.purplebeen.noteapp.features.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ActivityEditBinding
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater

/**
 * Created by baehy on 2018. 1. 25..
 */
class EditActivity : AppCompatActivity() {
    lateinit var mViewModel: EditViewModel
    lateinit var mBinding:ActivityEditBinding
    val position : Int by lazy {
        intent.extras.getInt("position")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        mViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
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