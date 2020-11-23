package com.pomelo.sleep.ui.main

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pomelo.sleep.BR
import com.pomelo.sleep.R
import com.pomelo.sleep.databinding.MainFragmentBinding
import com.pomelo.sleep.viewmodel.CountDownModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val TAG = "MainFragment"
    }

    private lateinit var viewModel: CountDownModel
    private lateinit var viewDataBinding: ViewDataBinding
    private lateinit var soundPool: SoundPool;
    private var load = 0;
    private var play = 0;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false)
        viewDataBinding.lifecycleOwner = this;
        soundPool = SoundPool(1,AudioManager.STREAM_MUSIC,0)
        load = soundPool.load(context, R.raw.clock, 1)
        soundPool.setOnLoadCompleteListener {
                soundPool, sampleId, status ->
                play = soundPool.play(load, 1f, 1f, 1, -1, 1f)
        }
        val handler = Handler()
        handler.postDelayed({
            soundPool.stop(play)
        },1 * 60 * 1000);
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountDownModel::class.java)
        // TODO: Use the ViewModel
        // TODO: Use the ViewModel
        viewDataBinding.setVariable(BR.viewmodel,viewModel)
        viewModel.curTime.observe(this, Observer {
//            Log.d(Companion.TAG, it.toString())

        })



    }

}