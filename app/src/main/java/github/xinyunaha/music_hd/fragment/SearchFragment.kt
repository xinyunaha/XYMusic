package github.xinyunaha.music_hd.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import github.xinyunaha.music_hd.R
import github.xinyunaha.music_hd.moudles.Toast
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        find_test.setOnClickListener{
//            find_test.text = "fuck"
//            Toast.toast(activity,"Fuck")
//        }
    }
}