package kr.purplebeen.noteapp.features.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var mViewModel: AddViewModel
    lateinit var mBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        mViewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        mBinding.viewModel = mViewModel

        observeViewModel()
    }

    fun observeViewModel() {
        mViewModel.saveButtonCallback.observe(this, Observer {
            mViewModel.save(mBinding.editTitle.text.toString(),
                            mBinding.editContent.text.toString())
        })

        mViewModel.finishCallback.observe(this, Observer {
            finish()
        })
    }

}
